import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MenuItem from 'material-ui/MenuItem';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Divider from 'material-ui/Divider';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import CodeMirror from 'react-codemirror';
import NavBar from './components/NavBar.jsx';
import Paper from 'material-ui/Paper';
import DropDownMenu from 'material-ui/DropDownMenu';
import EditorAce from './components/EditorAce.jsx';


export class Exercise extends React.Component {
    render() {
        return (
            <div>
                <NavBar />
                <Content />
            </div>
        );
    }
}

class Content extends React.Component {
    constructor() {
        super();
        this.state = {
            open: true,
            value: 1
        };
    }
    render() {
        return (
            <div id="content" className="content">
                <MuiThemeProvider muiTheme={getMuiTheme()}>
                    <div>
                        <div id="exercise">
                            <Card>
                                <CardHeader
                                    title="Exercise 1"
                                    className="loginheader"
									titleColor="white"
                                />
                                <Divider />
                                <CardText className="loginbody">
                                    <div>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                    Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
                                    Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
                                    Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
                                </div>
                                    <br />
                                    <div>
                                        <iframe width="560" height="315" src="https://www.youtube.com/embed/Ztc6_elMg60" frameBorder="0" allowFullScreen></iframe>
                                    </div>
                                </CardText>
                                <CardActions className="footer">
                                </CardActions>
                            </Card>
                        </div>
                        <br />
                        <div id="solution">
                            <Card>
                                <CardHeader
                                    title="Solution"
                                    className="loginheader"
									titleColor="white"
                                >
                                <div style={{textAlign: "right"}}>In Progress</div>
                                </CardHeader>
                                <Divider />
                                <CardText className="loginbody">
                                    <DropDownMenu disabled="true" value={this.state.value} onChange={this.handleChange} >
                                        <MenuItem value={1} primaryText="JavaScript" />
                                        <MenuItem value={2} primaryText="Java" />
                                    </DropDownMenu>
                                    <Paper zDepth={4}>
                                        <EditorAce />
                                    </Paper>
                                </CardText>
                                <CardActions className="footer">
                                    <RaisedButton label="Check"
									backgroundColor="#bd051f"
									labelColor="#FFFFFF"/>
                                </CardActions>    
                            </Card>
                        </div>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default Exercise;