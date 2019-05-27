import React, { Component } from "react";
import "./Token.css";
// import { Table, Divider, Tag } from "antd";
import TokenList from "./TokenList";
import { TOKEN_LIST_SIZE } from "../constants";
import {
  getUserCreatedTokens,
  getAllTokens,
  createToken
} from "../util/APIUtils";
import { Button, Input } from "antd";
class TokenComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      inputText: "",
      refreshChild: false,
      original: "",
      encrypted: "",
      tokens: [],
      page: 0,
      size: 10,
      totalElements: 0,
      totalPages: 0,
      last: true,
      isLoading: false
    };
    this.saveToken = this.saveToken.bind(this);
    this.onCellSelection = this.onCellSelection.bind(this);
    this.onDecrypt = this.onDecrypt.bind(this);
    this.onInputChange = this.onInputChange.bind(this);
  }

  saveToken(event) {
    if (this.state.inputText === this.state.original) {
      return;
    }
    let promise = createToken({ originalToken: this.state.inputText });
    promise
      .then(response => {
        const tokens = this.state.tokens.slice();
        this.setState({
          tokens: tokens.concat(response),
          refreshChild: !this.state.refreshChild
        });
      })
      .catch(error => {
        this.setState({
          isLoading: false
        });
      });
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
          // onChange={this.handleChange}
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
        <TokenList
          refreshChild={this.state.refreshChild}
          onClick={(encrypted, original) =>
            this.onCellSelection(encrypted, original)
          }
        />
      </div>
    );
  }
}

export default TokenComponent;
