/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from "react"
import {
  Platform,
  StyleSheet,
  Button,
  Text,
  View,
  Image
} from "react-native"
import { Quikkly, QuikklyView } from "react-native-quikkly"

const instructions = Platform.select({
  ios: "Press Cmd+R to reload,\nCmd+D or shake for dev menu",
  android: "Double tap R on your keyboard to reload,\nShake or press menu button for dev menu"
})

Quikkly.createImage({ value: 1234 }).then((result) => {
	// Do something with the image
})

export default class App extends Component {
  constructor(props) {
    super(props)

    this.state = {
      code: "<none>"
    }
  }

  onScanCode = () => {
    Quikkly.scanForResult().then((result) => {
      this.setState({ code: result.value })
    }).catch((err) => {
      this.setState({ code: "<cancelled>" })
    })
  }

  onScanCodeOverlay = (result) => {
    console.log(result);
    this.setState({ code: result.value})
  }

  render() {
    return (
      <View style={{flex: 1}}>
        <View style={styles.container}>
          <Text style={styles.welcome}>
          Welcome to Quikkly SDK {Quikkly.VERSION} Demo!
          </Text>
          <Text style={styles.instructions}>
          Tap the "Scan a code" button to open the full screen scanner
          or use the overlay scanner below to scan a tag
          </Text>
          <Text style={styles.instructions}>
          {instructions}
          </Text>
          <Button
            style={styles.action}
            onPress={this.onScanCode}
            title="Scan a code" />
          <Text style={styles.instructions}>
          The last scanned code was: {this.state.code}
          </Text>
        </View>
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

const styles = StyleSheet.create({
  container: {
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
  action: {
    color: "#330066"
  },
  overlay: {
    position: 'absolute'
  }
})
