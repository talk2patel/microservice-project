import React, { Component } from "react";
import { getAllTokens, getUserCreatedTokens } from "../util/APIUtils";
import LoadingIndicator from "../common/LoadingIndicator";
import { Button, Icon, notification } from "antd";
import { TOKEN_LIST_SIZE } from "../constants";
import { withRouter } from "react-router-dom";
import { Table, Divider, Tag } from "antd";

const { Column, ColumnGroup } = Table;

class TokenList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tokens: [],
      page: 0,
      size: 10,
      totalElements: 0,
      totalPages: 0,
      last: true,
      isLoading: false
    };
    this.onRowClick = this.onRowClick.bind(this);
  }

  loadTokens(page = 0, size = TOKEN_LIST_SIZE) {
    let promise;
    if (this.props.username) {
      if (this.props.type === "USER_CREATED_POLLS") {
        promise = getUserCreatedTokens(this.props.username, page, size);
      }
    } else {
      promise = getAllTokens(page, size);
    }

    if (!promise) {
      return;
    }

    this.setState({
      isLoading: true
    });

    promise
      .then(response => {
        const tokens = this.state.tokens.slice();
        this.setState({
          tokens: tokens.concat(response),
          page: response.page,
          size: response.size,
          totalElements: response.totalElements,
          totalPages: response.totalPages,
          last: response.last,
          isLoading: false
        });
      })
      .catch(error => {
        this.setState({
          isLoading: false
        });
      });
  }
  componentDidMount() {
    this.loadTokens();
  }
  columns = [
    {
      title: "Encrypted Token",
      dataIndex: "encryptedToken",
      key: token => {
        token.id();
      },
      render: token => <h2>{token}</h2>
    }
  ];

  onRowClick = (record, rowIndex) => {
    console.log("record, rowIndex:: ", record["encryptedToken"]);
    this.props.onClick(record["encryptedToken"], record["originalToken"]);
  };
  render() {
    return (
      <div>
        <Table
          rowKey={token => token.id}
          onRow={(record, rowIndex) => {
            return {
              onClick: event => {
                this.onRowClick(record, rowIndex);
              }, // click row
              onDoubleClick: event => {}, // double click row
              onContextMenu: event => {}, // right button click row
              onMouseEnter: event => {}, // mouse enter row
              onMouseLeave: event => {} // mouse leave row
            };
          }}
          columns={this.columns}
          dataSource={this.state.tokens}
        />
      </div>
    );
  }
}

export default withRouter(TokenList);
