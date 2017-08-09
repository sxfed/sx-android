'use strict';
import React, { Component } from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    ListView,
    Image,
    TouchableOpacity, // 不透明触摸
} from 'react-native';

// 获取屏幕宽度
var Dimensions = require('Dimensions');
const screenW = Dimensions.get('window').width;

// 常量设置
const cols = 3;
const cellH = 150;
const colSpace = 1;
const rowSpace = 1;
const cellW = (screenW - rowSpace * (cols + 1)) / cols;


export default class GridScreen extends Component {


       constructor(props) {
            super(props);
            const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
            this.state = {
                dataSource: ds.cloneWithRows([
                                { url: require('../image/ic0.png'), desc: '日常心理学'},
                                { url: require('../image/ic1.png'), desc: '用户推荐日报'},
                                { url: require('../image/ic2.png'), desc: '电影日报'},
                                { url: require('../image/ic3.png'), desc: '不许无聊'},
                                { url: require('../image/ic4.png'), desc: '设计日报'},
                                { url: require('../image/ic5.png'), desc: '大公司日报'},
                                { url: require('../image/ic6.png'), desc: '财经日报'},
                                { url: require('../image/ic7.png'), desc: '互联网安全'},
                                { url: require('../image/ic8.png'), desc: '开始游戏'},
                                { url: require('../image/ic9.png'), desc: '音乐日报'},
                                { url: require('../image/ic10.png'), desc: '动漫日报'},
                                { url: require('../image/ic11.png'), desc: '体育日报'},
                                { url: require('../image/ic12.png'), desc: '南方财报'}
                              ])
            };
       }


       render(){
            return (

              <ListView
                       dataSource={this.state.dataSource}
                       renderRow={this.renderRow}
                       contentContainerStyle={styles.listViewStyle}
              />
            );
       }


       renderRow(rowData){
            return (
                <TouchableOpacity activeOpacity={0.8} onPress={() => {alert(rowData.desc)}}>
                      <View style={styles.innerViewStyle}>
                             <Image style={styles.iconStyle} source={rowData.url} />
                                    <Text>{ rowData.desc }</Text>
                      </View>
                </TouchableOpacity>
            );
       }

}


const styles = StyleSheet.create({
    listViewStyle:{
        // 主轴方向
        flexDirection:'row',
        // 一行显示不下,换一行
        flexWrap:'wrap',
        // 侧轴方向
        alignItems:'center', // 必须设置,否则换行不起作用
        backgroundColor:"#F5FCFF",
    },

    innerViewStyle:{
        width:cellW,
        height:cellH,
        marginLeft:rowSpace,
        marginTop:colSpace,
        // 文字内容居中对齐
        alignItems:'center',
        padding:10,
        justifyContent: 'space-around',
        backgroundColor:"#8e8583",
    },

    iconStyle:{
        width:60,
        height:60,
    },

});