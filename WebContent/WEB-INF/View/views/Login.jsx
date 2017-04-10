import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import injectTapEventPlugin from 'react-tap-event-plugin';
import TextField from 'material-ui/TextField';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import { browserHistory } from 'react-router';
import Divider from 'material-ui/Divider';
import Avatar from 'material-ui/Avatar';
//import Background from './images/Unbenannt.PNG';

injectTapEventPlugin();

function handleClick(e) {
    fetch('http://localhost:8080/login', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'login',
        })
    }).then(function (response) {
      
    }).catch(function (err) {
        console.log(err)
    });
};

export class Login extends React.Component {
  render() {
    return (
      <div>
        <Header />
        <Content />
      </div>
    );
  }
}

//Navigation and AppBar

class Header extends React.Component {
  constructor() {
    super();
    this.state = {
      open: true
    };
  }

  render() {
    return (

      <div style={{ 'margin': '0' }}>
        <MuiThemeProvider muiTheme={getMuiTheme()}>
          <div>
            <AppBar 
			title="Colorado-Login" 
			iconElementLeft={<Avatar
                src="images/colorado.jpg"
                size={45}
			/>}
			style={{ 'backgroundColor': '#bd051f' }}  />
          </div>
        </MuiThemeProvider>
      </div>
    );
  }
}

class Content extends React.Component {
  constructor(props) {
    super(props);
    this.state = { value: '' };
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }
  handleClick(e) {
    e.preventDefault();
    //console.log('The link was clicked.');
    if (this.state.value === 's12345') {
      browserHistory.push('/exercise');
    }
    else if (this.state.value === 'd12345') {
      browserHistory.push('/docent');
    }
    else {
      alert("Username does not exist!");
    }
  }
  handleChange(event) {
    this.setState({ value: event.target.value });
  }
  /*componentWillMount() {
     console.log('Component WILL MOUNT!')
  }*/
  render() {
    return (
      <MuiThemeProvider muiTheme={getMuiTheme()}>
        <div id="content" style={{ 'marginLeft': '35%', 'marginRight': '35%', 'marginTop': '10%' }}>
          <Card>
            <CardHeader className="loginheader">
              <Avatar
                src="images/colorado.jpg"
                size={60}
				className="coloradologo"
                />
            </CardHeader>
            />
        <Divider />
            <CardText className="loginbody">
              <div >
                <TextField
                  className="logintext"
                  floatingLabelText="Username"
                  type='text'
                  id="username"
                  value={this.state.value}
                  onChange={this.handleChange}
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  style={{'width': '98%', 'font-size': '150%' }}
                  />
                <br />
                <br />
                <TextField
                  floatingLabelText="Password"
                  type="password"
                  id="password"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  style={{'width': '98%', 'font-size': '150%' }}
                  />
                <br />
                <br />
                
              </div>
            </CardText>
            <CardActions className="footer">
              <RaisedButton label="Login" onClick={this.handleClick} />
            </CardActions>
          </Card>
        </div>
      </MuiThemeProvider>
    );
  }
}

export default Login;