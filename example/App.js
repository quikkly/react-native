/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from "react"
import { createAppContainer} from 'react-navigation'
import {createStackNavigator} from 'react-navigation-stack'
import {
  Platform,
  StyleSheet,
  Button,
  Text,
  View,
  Image
} from "react-native"
import { Quikkly, QuikklyView } from "react-native-quikkly"

class HomeScreen extends React.Component {
  static navigationOptions = {
    title: 'Welcome',
  };

  onCreateCode = () => {
    console.log("creating code");
    Quikkly.createImage({
      value: 1234, /* required */
      template: "template0001style1", /* optional */
      skin: { /* optional */
          backgroundColor: "#ffffff",
          borderColor: "#333333",
          maskColor: "#ffffff",
          dataColor: ["#67ac5c"],
          overlayColor: "#333333",
          imageFile: "https://www.medicalnewstoday.com/content/images/articles/327/327448/maine-coon-cat.jpg"
      }
    }).then((result) => {
      console.log(result)
    },(error) => {
      console.log(error)
    })
  }

  render() {
    const {navigate} = this.props.navigation;
    return (
      <View>
        <Button
          title="Go to Overlay Scanner"
          onPress={() => navigate('Overlay', {name: 'overlay'})}
        />
        <Button
          title="Create Image"
          onPress={() => this.onCreateCode()}
        />
        <Text style={{textAlign: 'center',color: '#333333',marginBottom: 5,}}>
        Click to see an example of the overlay scanner.
        </Text>
      </View>
);
  }
}

class OverlayScreen extends React.Component {
  static navigationOptions = {
    title: 'Overlay',
  };

  constructor(props) {
    super(props)

    this.state = {
      code: "<none>"
    }
  }

  onScanCodeOverlay = (result) => {
    console.log(result);
    this.setState({ code: result.value})
  }

  render() {
    const {navigate} = this.props.navigation;
    return (
      <View style={{flex: 1}}>
        <Button
          title="Go back home"
          onPress={() => navigate('Home', {name: 'home'})}
        />
          <Text style={{textAlign: 'center',color: '#333333',marginBottom: 5,}}>
          The last scanned code was: {this.state.code}
          </Text>
        <View style={{flex:1, alignItems: 'center'}}>
          <QuikklyView 
            style={{width: '100%', height: '100%'}} 
            onScanCode={this.onScanCodeOverlay} 
            cameraPreviewFit={2}/>
          <Image 
            style={{width: '100%', height: '100%', position: 'absolute'}}
            source={require('./images/mask2.png')}
          />
        </View>
      </View>
    );
  }
}

const MainNavigator = createStackNavigator({
  Home: {screen: HomeScreen},
  Overlay: {screen: OverlayScreen},
});

const instructions = Platform.select({
  ios: "Press Cmd+R to reload,\nCmd+D or shake for dev menu",
  android: "Double tap R on your keyboard to reload,\nShake or press menu button for dev menu"
})

const App = createAppContainer(MainNavigator);
export default App;

