import { NativeModules } from "react-native"

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

export const QuikklyError = {
  NO_CONTEXT: "QuikklyNoContext",
  INVALID_CONTEXT: "QuikklyInvalidContext",
  CANCELLED: "QuikklyCancelled",
  UNKNOWN: "QuikklyUnknown"
}
