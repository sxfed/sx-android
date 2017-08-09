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
  Button,
} from 'react-native';



export default class HomeScreen extends React.Component {



    // Configuring the Header
    render() {
       const {navigate} = this.props.navigation;
       return (
            <View style={styles.text}>
                <View style={styles.btnWidth}>
                    <Button
                        onPress={() => navigate('Tab')}
                        color="#841584"
                        title="TabarNavigator Guide" />
                </View>

                <View style={styles.btnMarginTop}>
                                    <Button
                                        onPress={() => navigate('Ant')}
                                        color="#841584"
                                        title="Ant design" />
                                </View>


                <View style={styles.title}>
                    <Button
                       onPress={() => navigate('Chat', { user: "Lucy" })}
                       color="#F41584"
                       title="Navigation title" />
                </View>

                <View style={styles.title}>
                                    <Button
                                       onPress={() => navigate('Grid', { user: "Lucy" })}
                                       color="#F41584"
                                       title="Gride Layout" />
                                </View>
                <View style={styles.title}>
                                                    <Button
                                                       onPress={() => navigate('Ref')}
                                                       color="#F41584"
                                                       title="referesh list" />
                                                </View>

                <View style={styles.title}>
                                    <Button
                                       onPress={() => navigate('List')}
                                       color="#F41584"
                                       title="list" />
                                </View>

            </View>
       )
    };
}


const styles = StyleSheet.create({
  text:{
          flex:1,
          alignItems:'center',
          flexDirection:'column',
  },
  btnWidth: {
    width:'100%',
  },

  btnMarginTop: {
      width:'100%',
      marginTop:15,
    },

  title: {
      marginTop:15,
      width:'100%',
    }

});