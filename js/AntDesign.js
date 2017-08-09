'use strict';

import React, { Component } from 'react';
import {
  AppRegistry,
  Text,
  StyleSheet,
  Image,
  View,
} from 'react-native';

import { Button } from 'antd-mobile';
var REQUEST_URL = 'https://raw.githubusercontent.com/facebook/react-native/master/docs/MoviesExample.json';

export default class AntDesign extends React.Component {

    constructor(props) {
          super(props);

          this.state = {
              movies: null,
          };
        }

    componentDidMount(){
       this.fetchData();
    }


    render() {
        if (!this.state.movies) {
           return this.renderLoadingView();
        }

        var movie = this.state.movies[0];

        return this.renderMovie(movie);
    }


    renderLoadingView (){
            return (
                        <View style={styles.container}>
                        <Text>
                        正在加载电影数据......
                        </Text>
                        </View>
                        );
        }

    renderMovie(movie){
         return (
                 <View style={styles.container}>
                        <Image source={{uri:movie.posters.thumbnail}}
                        style={styles.thumbnail} />
                        <View style={styles.rightContainer}>
                            <Text style={styles.title}>{movie.title}</Text>
                            <Text style={styles.year}>{movie.year}</Text>
                        </View>
                 </View>
         );
    }

    fetchData(){
        this.getMoviesFromApiAsync(REQUEST_URL);
    }



    // es6 Get方式
    getMoviesFromApiAsync(url) {
        return fetch(url)
                .then((response) => response.json())
                .then((responseJson) => {
                    this.setState({
                       movies:responseJson.movies,
                    });
                })
                .catch((error) => {
                     console.error(error);
                });
    }

    // 网络POST请求
    getRespDataByPost(){
         let formData = new FormData();
         formData.append("name","admin");
         formData.append("password","admin123");

        fetch(url , {
          method: 'POST',
          headers: {
                'Accept': 'application/json',
                 'Content-Type': 'application/json',
          },
          body: formData,
        }).then((response) => {
          if (response.ok) {
              return response.json();
          }
        }).then((json) => {
          alert(JSON.stringify(json));
        }).catch((error) => {
          console.error(error);
        });
    }


    // 注意这个方法前面有async关键字,es7 Get方式
    async getMoviesFromApi() {
      try {
            // 注意这里的await语句，其所在的函数必须有async关键字声明
            let response = await fetch(REQUEST_URL);
            let responseJson = await response.json();
            this.setState({
                 movies:responseJson.movies,
            });
      } catch(error) {
            console.error(error);
      }
    }

}


const styles = StyleSheet.create({

    container:{
            flex:1,flexDirection:'row',justifyContent:'center',alignItems:'center',backgroundColor:'#F5FCFF'
        },
    thumbnail:{
        width:100,height:80
    },
    rightContainer:{
        flex:1
    },
    title:{
        fontSize:20,marginBottom:8,textAlign:'center'
    },
    year:{
        textAlign:'center'
    },
});