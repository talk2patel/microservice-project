import React, { Component } from "react";
import { getAllTokens, getUserCreatedTokens } from "../util/APIUtils";
import { TOKEN_LIST_SIZE } from "../constants";
import { withRouter } from "react-router-dom";
import { Table, Divider, Tag } from "antd";

class TokenTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tokens: [],
      refresh: false,
      page: 0,
      size: 10,
      totalElements: 0,
      totalPages: 0,
      last: true
    };
    this.onRowClick = this.onRowClick.bind(this);
    this.loadTokens = this.loadTokens.bind(this);
  }

  loadTokens(page = 0, size = TOKEN_LIST_SIZE) {
    let promise;
    console.log("this.props.username:: ", localStorage.getItem("username"));
    console.log("this.props.type::", this.props.type);
    if (localStorage.getItem("username")) {
      if (this.props.type === "USER_CREATED_TOKENS") {
        promise = getUserCreatedTokens(
          localStorage.getItem("username"),
          page,
          size
        );
      } else {
        promise = getAllTokens(page, size);
      }
    } else {
      promise = getAllTokens(page, size);
    }

    promise
      .then(response => {
        const tokens = [];
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
        console.log("Error while loading tokens", error);
      });
  }
  componentDidMount() {
    this.loadTokens();
  }

  componentWillReceiveProps(props) {
    const { refreshChild } = this.props;
    if (props.refreshChild !== this.state.refresh) {
      this.loadTokens();
      this.setState({
        refresh: !this.state.refresh
      });
    }
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
    // console.log("record, rowIndex:: ", record["encryptedToken"]);
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

export default withRouter(TokenTable);
