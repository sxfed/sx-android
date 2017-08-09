'use strict';

import React, { Component } from 'react';
import {
  AppRegistry,
  Text,
  StyleSheet,
  Button,
  View,
} from 'react-native';
//title: isInfo ? `${user}'s Contact Info` : `Chat with ${state.params.user}`,



export default class Chat extends React.Component {
    // Setting the Header Title,
    static navigationOptions = ({ navigation }) => {
        const { state, setParams } = navigation;
        const isInfo = state.params.mode ==='info';
        const { user } = state.params;

        return {
            headerTitle: isInfo ? `${user}'s Contact Info` : `Chat with ${state.params.user}`,
            headerTitleStyle: { fontSize:20,color:'red', alignSelf:'center'},
            headerRight: (
                <Button
                    title={isInfo ? 'Done' : 'edit'}
                    onPress={() => setParams({ mode: isInfo ? 'none': 'info' })}
                />
            ),
        };
    };

    render() {
        const { params } = this.props.navigation.state;
        return (
                    <View>
                            <Text>Hello, { params.user } chat </Text>
                    </View>
                );
    }
}