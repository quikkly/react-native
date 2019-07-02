import React, { Component } from "react"
import { requireNativeComponent, NativeModules } from "react-native"

const QuikklyManager = NativeModules.QuikklyManager

export class Quikkly {
  static get VERSION() {
    return QuikklyManager.VERSION
  }

  static scanForResult(options = {}) {
    return QuikklyManager.scanForResult(options)
  }

  static createImage(options = {}) {
    return QuikklyManager.createImage(options)
  }
}

export class QuikklyView extends Component {
  _onScanCode = (event) => {
    if(this.props.onScanCode) {
      this.props.onScanCode(event.nativeEvent)
    }
  }
  
  render() {
    return (
      <QuikklyScanViewManager
        {...this.props}
        onScanCode={this._onScanCode}
      />
    );
  }
}

const QuikklyScanViewManager = requireNativeComponent("QuikklyScanView", QuikklyView)

export const QuikklyError = {
  NO_CONTEXT: "QuikklyNoContext",
  INVALID_CONTEXT: "QuikklyInvalidContext",
  CANCELLED: "QuikklyCancelled",
  UNKNOWN: "QuikklyUnknown"
}
