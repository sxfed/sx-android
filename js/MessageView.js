'use strict';

import React, { Component } from 'react';
import {
      StyleSheet,
      Text,
      View
} from 'react-native';

import { RefreshControl, ListView } from 'antd-mobile';


const data = [
  {
    img: 'https://zos.alipayobjects.com/rmsportal/dKbkpPXKfvZzWCM.png',
    title: 'Meet hotel',
    des: '不是所有的兼职汪都需要风吹日晒',
  },
  {
    img: 'https://zos.alipayobjects.com/rmsportal/XmwCzSeJiqpkuMB.png',
    title: 'McDonald\'s invites you',
    des: '不是所有的兼职汪都需要风吹日晒',
  },
  {
    img: 'https://zos.alipayobjects.com/rmsportal/hfVtzEhPzTUewPm.png',
    title: 'Eat the week',
    des: '不是所有的兼职汪都需要风吹日晒',
  },
];


let index = data.length - 1;

let pageIndex = 0;


export default class MessageView extends Component {

    constructor(props){
        super(props);
        const dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.initData = [];
        for (let i = 0; i < 20; i++) {
            this.initData.push(`r${i}`);
        }

        this.state = {
            dataSource: dataSource.cloneWithRows(this.initData),
            refreshing: false,
        };
    }


    componentDidMount() {
        this.manuallyRefresh = true;
        setTimeout(() => this.setState({ refreshing: true }), 10);
    }


    onRefresh = () => {
        console.log('onRefresh');
        if (!this.manuallyRefresh) {
          this.setState({ refreshing: true });
        } else {
          this.manuallyRefresh = false;
        }
        setTimeout(() => {
          this.initData = [`ref${pageIndex++}`, ...this.initData];
          this.setState({
            dataSource: this.state.dataSource.cloneWithRows(this.initData),
            refreshing: false,
          });
        }, 1000);
      };




    render(){
        const separator = (sectionID, rowID) => (
              <div
                key={`${sectionID}-${rowID}`}
                style={{backgroundColor: '#F5F5F9',
                                  height: 8,}} />
            );

        const row = (rowData, sectionID, rowID) => {
              if (index < 0) {
                index = data.length - 1;
              }
              const obj = data[index--];
              return (
                <div key={rowID}
                  style={{
                    padding: '0.08 0.16',
                    backgroundColor: 'white',
                  }}
                >
                  <h3 style={{ padding: 2, marginBottom: '2', }}>
                    {obj.title}
                  </h3>
                  <div style={{ display: 'flex' }}>
                    <img style={{ height: '1.28', marginRight: '0.08' }} src={obj.img} alt="icon" />
                    <div style={{ display: 'inline-block' }}>
                      <div style={{ margin: '0.1 0 0.2 0' }}>{obj.des}-{rowData}</div>
                      <div><span style={{ fontSize: '1.6', color: '#FF6E27' }}>35</span>元/任务</div>
                    </div>
                  </div>
                </div>
              );
        };


        return (
              <ListView
                dataSource={this.state.dataSource}
                renderRow={row}
                renderSeparator={separator}
                initialListSize={5}
                pageSize={5}
                scrollRenderAheadDistance={200}
                scrollEventThrottle={20}
                scrollerOptions={{ scrollbars: true }}
                refreshControl={<RefreshControl
                  refreshing={this.state.refreshing}
                  onRefresh={this.onRefresh}
                />}
              />
        );

    }

}