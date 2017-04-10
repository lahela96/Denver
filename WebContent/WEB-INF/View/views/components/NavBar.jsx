import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import Divider from 'material-ui/Divider';
import RaisedButton from 'material-ui/RaisedButton';
import DropDownMenu from 'material-ui/DropDownMenu';
import {white, red500, black, green} from 'material-ui/styles/colors';
import ActionPerson from 'material-ui/svg-icons/action/account-circle';
import ActionCheck from 'material-ui/svg-icons/action/check-circle';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import { browserHistory } from 'react-router';
import Avatar from 'material-ui/Avatar';

const lecturelist = [];
const exerciseslist = [];
var data;

const personStyles = {
  marginRight: 24,
  marginTop: 12,
};

const iconStyles = {
  marginRight: 12,
};

class NavBar extends React.Component {
    constructor() {
        super();
        this.state = {
            open: true,
            value: 1,
			dropdown: 0
        };
		this.handleChange = this.handleChange.bind(this);
    }
	
	componentWillMount() {
		var dropdownjson = '{"lecture": [{"lecture_title":"Datenbanken","lecture_id": "0001","exercises": [{ "exercise_title":"Fibonacci", "exercise_id":"0123123"},{ "exercise_title":"Test2", "exercise_id":"0123124"},{ "exercise_title":"Test3", "exercise_id":"0123125"}]},{"lecture_title":"Webprogrammierung","lecture_id": "0002","exercises": [{ "exercise_title":"WEB1", "exercise_id":"0123123"},{ "exercise_title":"WEB2", "exercise_id":"1123124"},{ "exercise_title":"WEB3", "exercise_id":"1123125"}]},{"lecture_title":"Test","lecture_id": "0003","exercises": [{ "exercise_title":"Test1", "exercise_id":"2123123"},{ "exercise_title":"Test2", "exercise_id":"2123124"},{ "exercise_title":"Test3", "exercise_id":"2123125"}]}]}';
		data = JSON.parse(dropdownjson);
		for(var i=0;i<data.lecture.length;i++){
			lecturelist.push(<MenuItem value={i} key={i} primaryText={data.lecture[i].lecture_title} />);
		}
		for(var j=0;j<data.lecture[0].exercises.length;j++){
			exerciseslist.push(<MenuItem value={j} key={j} primaryText={data.lecture[0].exercises[j].exercise_title} />);
		}
    }
	
	handleChange(event, index, value) {
		var count = exerciseslist.length;
		for(var i =0;i<count;i++){
			exerciseslist.pop();
		}
		for(var j=0;j<data.lecture[value].exercises.length;j++){
			exerciseslist.push(<MenuItem value={j} key={j} primaryText={data.lecture[value].exercises[j].exercise_title} />);
		}
		
		this.setState({dropdown: value});
	}

    onClick(e){
       e.preventDefault();
	   if(e.target.textContent == "Settings"){
       browserHistory.push('/user');
	   }
	   else browserHistory.push('/');
    }

	 
    render() {
        return (
            <div style={{ 'margin': '0' }}>
                <MuiThemeProvider muiTheme={getMuiTheme()}>
                    <div>
                        <AppBar 
                            title="Colorado"
                            style={{ 'position': 'fixed','backgroundColor': '#bd051f', 'opacity':'0.9'}}
							iconElementLeft={<Avatar
							src="images/colorado.jpg"
							size={45}
							/>}
                            iconElementRight={
                                <IconMenu
                                    iconButtonElement={<IconButton>
                                                        <ActionPerson style={personStyles} color={white} hoverColor={black}/>
                                                    </IconButton>}
                                    targetOrigin={{horizontal: 'right', vertical: 'top'}}
                                    anchorOrigin={{horizontal: 'right', vertical: 'top'}}
                                    >
                                    <MenuItem primaryText="Settings" onClick={this.onClick} />
                                    <MenuItem primaryText="Sign out" onClick={this.onClick} />
                                </IconMenu>      
                                }
                            />

                        <Drawer
                            id="drawer"
                            docked={true}
                            open={this.state.open}
                            onRequestChange={(open) => this.setState({ open })}
                            containerStyle={{ 'position': 'fixed', 'margin': '0', 'top': '64px', 'height': 'calc(100% - 64px)', 'backgroundColor' : '#959595' }}
                            >
                            <DropDownMenu value={this.state.dropdown} onChange={this.handleChange} >
                                {lecturelist}
                            </DropDownMenu>
                            <Divider />
                            {exerciseslist}
							
                        </Drawer>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default NavBar;