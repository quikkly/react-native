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
  View
} from "react-native"
import { Quikkly } from "react-native-quikkly"

const instructions = Platform.select({
  ios: "Press Cmd+R to reload,\nCmd+D or shake for dev menu",
  android: "Double tap R on your keyboard to reload,\nShake or press menu button for dev menu"
})

let svgImage = Quikkly.createImage({ value: 1234 })

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

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to Quikkly SDK {Quikkly.VERSION} Demo!
        </Text>
        <Text style={styles.instructions}>
          To get started, tap the button or edit App.js
        </Text>
        <Text style={styles.instructions}>
          {instructions}
        </Text>
        <Button
          style={styles.action}
          onPress={this.onScanCode}
          title="Scan a code"
        />
        <Text style={styles.instructions}>
          The last scanned code was: {this.state.code}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
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
  }
})
