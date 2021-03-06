import React, { Component } from "react";
import "./Token.css";
import TokenTable from "./TokenTable";
import { createToken } from "../util/APIUtils";
import { Button, Input } from "antd";
class TokenComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inputText: "",
      refreshChild: false,
      original: "",
      encrypted: "",
      tokens: []
    };
    this.saveToken = this.saveToken.bind(this);
    this.onCellSelection = this.onCellSelection.bind(this);
    this.onDecrypt = this.onDecrypt.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
  }

  saveToken(event) {
    if (this.state.inputText === this.state.original) {
      this.setState({
        inputText: "",
        encrypted: "",
        original: ""
      });
    } else {
      let promise = createToken({ originalToken: this.state.inputText });
      promise
        .then(response => {
          const tokens = this.state.tokens.slice();
          this.setState({
            tokens: tokens.concat(response),
            refreshChild: !this.state.refreshChild,
            inputText: ""
          });
        })
        .catch(error => {
          this.setState({
            isLoading: false
          });
        });
    }
  }

  onCellSelection(encrypted, original) {
    console.log("onCellSelection:: ", encrypted, original);
    this.setState({
      inputText: encrypted,
      original: original,
      input: encrypted
    });
  }

  onDecrypt() {
    if (this.state.inputText === this.state.original) {
      return;
    }
    this.setState({
      inputText: this.state.original
    });
  }

  onInputChange(event) {
    this.setState({
      inputText: event.target.value
    });
  }

  render() {
    return (
      <div className="token-content">
        <Input
          className="input-token"
          size="large"
          value={this.state.inputText}
          onChange={this.onInputChange}
          placeholder="Enter a token"
        />
        <Button
          className="encrypt-button"
          onClick={event => this.saveToken(event)}
          type="primary"
          size="large"
        >
          Encrypt
        </Button>
        <Button
          className="decrypt-button"
          onClick={this.onDecrypt}
          type="primary"
          size="large"
        >
          Decrypt
        </Button>
        <TokenTable
          refreshChild={this.state.refreshChild}
          onClick={(encrypted, original) =>
            this.onCellSelection(encrypted, original)
          }
          type="USER_CREATED_TOKENS"
        />
      </div>
    );
  }
}

export default TokenComponent;
