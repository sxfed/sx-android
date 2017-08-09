'use strict';

import React, { Component } from 'react';
import {
  AppRegistry,
  View,
  Text,
  StyleSheet,
  ListView,
  Image,
  TouchableHighlight,
} from 'react-native';



/**
*   1.dataSource        设置数据源，
    2.renderRow         渲染行
    3.renderHeader      渲染头部
    4.renderSeparator   渲染分割线
    5.initialListSize   设置初始渲染的行数

    详细说明：
    ListView数据列表
        ListView最重要的是设置每行显示的组件
        section ， header
        使用ListView.DataSource数据源，给他传递一个普通的数组，再使用DataSource来实例化一个ListView组件。
        ListView实现：
        （1）行、section，组件设置
        （2）设置数据
        设置ListView数据源的时候需要借助state属性
        需要将DataSource对象设置为state属性,ListView会根据数据源进行渲染

*/

export default class ListComponent extends Component {

    constructor(props) {
        super(props);
        /*判断这两行是否相同，就是是否发生变化，决定渲染哪些行组件，避免全部渲染，提高渲染效率*/
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = {
              /*不使用原始数据，有一个拷贝的过程*/
              /*使用复制后的数据源实例化ListView。优势：当原始数据发生变化时，那ListView组件的DataSource不会改变*/
              /*是一个数组*/
              dataSource: ds.cloneWithRows([
                'John', 'Joel', 'James', 'Jimmy', 'Jackson', 'Jillian', 'Julie', 'Devin' ,'jack' ,'marry','tom','mike','lilei'
              ])
        };

    }



    // 渲染行组件
    _renderRow = (rowData,sectionID,rowID) => {
        return(
            <TouchableHighlight onPress={() => this._pressRow(rowData,sectionID,rowID)}>
            <View style={styles.row}>
            <Image style={styles.thumbnail} source={require('../icon_stub.png')}/>
                            <View style={styles.conDesc}>
                                    <Text>{rowData}</Text>
                                    <Text>测试列表Item说明…………</Text>
                            </View>
            </View>
            </TouchableHighlight>
        );
    }

    _pressRow = (rowData,sectionID,rowID) => {

        if(rowID == '0'){
            alert(rowData);
        }else if(rowID == '2'){
            alert('' + rowID);
        }else{
            //this.props.listToHome(rowData);
            alert('' + rowID);
        }

    }


    //渲染头部
    _renderHeader (){
        return(
            <View>
                <Text>user  List</Text>
                <View></View>
            </View>
        );
    }


    //渲染分割线
    _renderSeparator (sectionId,rowId){
        return (
            <View style={styles.separator}
            key={sectionId+rowId}>
            </View>
        );
    }

    render(){

        return (
                   <View>
                           <ListView style={styles.listView}
                             dataSource={this.state.dataSource}
                             renderRow={(rowData,sectionID,rowID) => this._renderRow(rowData,sectionID,rowID)}
                             renderHeader={this._renderHeader}
                             renderSeparator={this._renderSeparator} />
                    </View>

        );
    }




}


const styles = StyleSheet.create({
  listView:{
          marginTop:25,
          backgroundColor:"#F5FCFF"
  },

  row: {
    flexDirection:"row",
    padding:12,
    backgroundColor:"#F5FCFF"
  },
  thumbnail: {
       width:75,
       height:75,
       backgroundColor:"gray"
  },
  conDesc: {
    marginLeft:20
  },

  separator: {
      height:1,
      backgroundColor:"#8e8583"
  }

});