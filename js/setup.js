'use strict';

import React, { Component } from 'react';
import {
  AppRegistry,
  View,
  Text,
  StyleSheet,
  Button,
  NativeModules,
  DeviceEventEmitter,
  TouchableNativeFeedback,
} from 'react-native';

import { StackNavigator,TabNavigator } from 'react-navigation';
import ListViewBasics from './ListComponent';
import HomeScreen from './HomeScreen';
import Chat from './Chat';
import RecentChatsScreen from './RecentChatsScreen';
import AllContactsScreen from './AllContactsScreen';
import AntDesign from './AntDesign';
import GridScreen from './GridScreen';
//import MessageView from './MessageView';

class Root extends Component {
    static navigationOptions = {
        title: 'React-native Demo',
        headerLeft: <Button title="关闭" onPress={() => NativeModules.commModule.closeActivity()}></Button>,
    }

    state = {
        pageIndex: 0
    }

    componentWillMount() {
        this.getNativeData();
    }

    getNativeData() {
        NativeModules.commModule.jsActivity(
            (successMsg) => {
                this.setState({
                        pageIndex: successMsg
                    }
                );
            },
            (erroMsg) => {
                alert(erroMsg)
            }
        );
    }

    componentWillMount(){
        DeviceEventEmitter.addListener('SendEvent', (event)=> {
            alert("send success" + ",name: " + event["name"] + ",sex: " + event["sex"] + ",age: " + event["age"]);
        });
    }

    callNative(){
        NativeModules.commModule.nativeCallMethod();
    }

    /**
     * 调用原生代码
     */
    skipNativeCall() {
        let phone = '18637070949';
        NativeModules.commModule.rnCallNative(phone);
    }

//        toHome = (rowData) => {
//            this.props.navigation.navigate('Home', {rowData});
//        }

    toChat = () => {
        this.props.navigation.navigate('Chat');
    }

    render() {
        if (this.state.pageIndex == 1) {　　//根据传递过来的数据跳转不同的页面
            return (
                <View style={styles.container}><Text style={styles.welcome}>第一页</Text>
                    <Button title="测试" onPress={() => NativeModules.commModule.showToast('nihao',1)} />
                    <Button title="关闭" onPress={() => NativeModules.commModule.closeActivity()} />
                    <Text style={styles.welcome} onPress={this.skipNativeCall.bind(this)}>
                                跳转到拨号界面
                            </Text>

                    <Text style={styles.descText} onPress={this.callNative.bind(this)}>
                        点击我，接收原生发送过来的消息，并弹出提示框来表示
                    </Text>
                    <Text style={styles.descText} onPress={this.toChat}>
                        点击我,聊天界面
                    </Text>
                </View>
            );
        } else {
            return (<HomeScreen navigation={this.props.navigation} />);
        }
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#65A35F',
  },
  welcome: {
    fontSize: 40,
    textAlign: 'center',
    margin: 10,
  },
  titleText: {
      fontSize: 20,
      fontWeight: 'bold',
    },
    descText: {
          fontSize: 14,
          textAlign: 'center',
          margin: 10
        },
});


const MainScreenNavigator = TabNavigator({
  Recent: { screen: RecentChatsScreen },
  All: { screen: AllContactsScreen },
});


export default StackNavigator({
    Root: { screen: Root },
    Home: { screen: HomeScreen },
    Chat: { screen: Chat},
    Tab: { screen: MainScreenNavigator},
    Ant: { screen: AntDesign},
    Grid: { screen: GridScreen},
    List: { screen: ListViewBasics},
});
