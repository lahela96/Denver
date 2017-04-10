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


const personStyles = {
  marginRight: 24,
  marginTop: 12,
};

const iconStyles = {
  marginRight: 12,
};

class DocentBar extends React.Component {
    constructor() {
        super();
        this.state = {
            open: true,
            value: 1,
			dropdown: 0
        };
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

                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default DocentBar;