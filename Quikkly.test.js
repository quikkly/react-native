import React from "react"
import { Quikkly } from "./Quikkly"

import renderer from "react-test-renderer"

it("version without crashing", () => {
  const version = Quikkly.VERSION

  expect(version).toBeTruthy()
})
