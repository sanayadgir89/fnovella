import React from "react";
import ListItem from "./ListItem";
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import IconButton from 'material-ui/IconButton';
import Search from 'material-ui/svg-icons/action/search';
import MenuItem from 'material-ui/MenuItem';
import {usersGetRequest, usersGetRequestBySearch, usersDeleteRequest} from '../../../../../actions';
import Pagination from '../../../../../components/Pagination'

/** *
 * Fake element list render....
 * */

let size = 5; //limit
let number = 0; //page
class ListElements extends React.Component {
  constructor(props) {
    super(props);
    this.onDeleteButton = this
      .onDeleteButton
      .bind(this);
    this.handleSearch = this
      .handleSearch
      .bind(this);
    this.state = {
      value: 2,
      searchValue: 'Name',
      inputValue: ''
    };

  }

  componentWillMount() {
    console.log("running component will mount");
    // API action
    this
      .props
      .actions
      .usersGetRequest(number, size);
  }
  onDeleteButton(id) {
    console.log("id: ", id);
    this
      .props
      .actions
      .usersDeleteRequest(id);
  }
  handleSearch(e) {
    e.preventDefault();
    switch (this.state.searchValue) {
      case "Id":
        this
          .props
          .actions
          .usersGetRequestBySearch(this.state.inputValue, null, null);
        break;
      case "Name":
        this
          .props
          .actions
          .usersGetRequestBySearch(null, this.state.inputValue, null);
        break;
      case "Code":
        this
          .props
          .actions
          .usersGetRequestBySearch(null, null, this.state.inputValue);
        break;
      default:
        this
          .props
          .actions
          .usersGetRequestBySearch();
        break;
    }
  }
  render() {
<<<<<<< HEAD
    let i =1;
=======
    console.log("users: ", this.props.users.content)
    let i = 0;
>>>>>>> 6389a761b50e8dc802df2b328886e1bd8aa3892c
    return (
      <article className="article">
        <h2 className="article-title">Lista de usuarios</h2>
        <div className="row">
          <div className="col-xl-12">
            <div className="box box-transparent">
<<<<<<< HEAD
=======

              <form onSubmit={this.handleSearch}>

                <div className="row">
                  <div className="col-xl-5">
                    <div >Basic table</div>
                  </div>
                  <div className="col-xl-7 text-right">
                    <input
                      style={{
                      margin: 5,
                      padding: 5
                    }}
                      type='text'
                      value={this.state.inputValue}
                      onChange={(e) => {
                      this.setState({inputValue: e.target.value})
                    }}/>
                    <select
                      style={{
                      padding: 5,
                      margin: 5,
                      height: 34
                    }}
                      onChange={(e) => {
                      this.setState({searchValue: e.target.value})
                    }}
                      value={this.state.searchValue}>
                      <option value="Name">Name</option>
                      <option value="Id">Id</option>
                      <option value="Code">Code</option>
                    </select>
                    <IconButton
                      iconStyle={{
                      color: 'white'
                    }}
                      style={{
                      margin: 5,
                      height: 34,
                      width: 34,
                      backgroundColor: '#49a54e',
                      padding: 5
                    }}
                      type="submit"
                      className="btn btn-primary"><Search/></IconButton>
                  </div>
                </div>
              </form>

>>>>>>> 6389a761b50e8dc802df2b328886e1bd8aa3892c
              <div className="box-body no-padding-h">

                <div className="box box-default table-box mdl-shadow--2dp">
                  <table className="mdl-data-table">
                    <thead>
<<<<<<< HEAD
                    <tr>
                      <th className="mdl-data-table__cell--non-numeric">#</th>
                      <th className="mdl-data-table__cell--non-numeric">FirstName</th>
                      <th className="mdl-data-table__cell--non-numeric">LastName</th>
                      <th className="mdl-data-table__cell--non-numeric">Email</th>
                      <th className="mdl-data-table__cell--non-numeric">CellPhone</th>
                      <th className="mdl-data-table__cell--non-numeric">Gender</th>
                    </tr>
=======
                      <tr>
                        <th className="mdl-data-table__cell--non-numeric">#</th>
                        <th className="mdl-data-table__cell--non-numeric">Id</th>
                        <th className="mdl-data-table__cell--non-numeric">FirstName</th>
                        <th className="mdl-data-table__cell--non-numeric">LastName</th>
                        <th className="mdl-data-table__cell--non-numeric">Email</th>
                        <th className="mdl-data-table__cell--non-numeric">CellPhone</th>
                        <th className="mdl-data-table__cell--non-numeric">Gender</th>
                      </tr>
>>>>>>> 6389a761b50e8dc802df2b328886e1bd8aa3892c
                    </thead>

                    <tbody>

                      {this.props.users.content
                        ? this
                          .props
                          .users
                          .content
                          .map((user) => {
                            return <ListItem
                              key={user.id}
                              onDelete={this.onDeleteButton}
                              number={i++}
                              onEdit={this.props.onEdit}
                              userData={user}/>
                          })
                        : null
}
                    </tbody>
                  </table>

                  <Pagination
                    totalPages={this.props.users.totalPages}
                    totalElements={this.props.users.totalElements}
                    getRequest={this.props.actions.usersGetRequest}/>

                </div>
              </div>
            </div>
          </div>

        </div>

      </article>
    );
  }

}
function mapStateToProps(state) {
  //pass the providers
  return {users: state.users}
}

/* Map Actions to Props */
function mapDispatchToProps(dispatch) {
  return {
    actions: bindActionCreators({
      usersGetRequest,
      usersDeleteRequest,
      usersGetRequestBySearch
    }, dispatch)
  };
}

module.exports = connect(mapStateToProps, mapDispatchToProps)(ListElements);
