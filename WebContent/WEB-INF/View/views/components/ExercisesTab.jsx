import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import Solution from './Solution.jsx';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import Fetch from 'react-fetch';
import EditorAce from './EditorAce.jsx';

function handleClick(e) {
    fetch('http://localhost:8080/exercise', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'exercise',
            crud: '1',
            title: 'FrontEnd',
            description: 'Test 1234',
            code: 'bam bam baaaaam'
        })
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
};


class ExercisesTab extends React.Component {

    constructor() {
        super();
        this.state = {
            open: true,
			selectedProgrammingLanguage: 1,
            value: 1,
			language: 'javascript'
        };
		this.handleChangeProgrammingLanguage = this.handleChangeProgrammingLanguage.bind(this);
    }
	
	handleChangeProgrammingLanguage(event, index, value){
		this.setState({selectedProgrammingLanguage: value});
		if(value===1){
			this.setState({language:'javascript'});
		}
		else if(value===2){
			this.setState({language:'java'});
		}
	}
	
    render() {
        return (
            <div>
                <Card className="card">
                    <Divider />
                    <CardText className="loginbody">
                        <h4>Main Information</h4>
                        <Paper zDepth={2} style={{ background: "#d1d1d1", padding: "2%" }}>
                            <table style={{ width: "100%", verticalAlign: "top" }}>
                                <tbody>
                                    <tr>
                                        <td style={{ width: "50%", padding: "6px", paddingTop: "0" }}>
                                            <TextField
                                                floatingLabelText="Title"
                                                fullWidth={false}
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                        <td style={{ width: "50%", padding: "6px", verticalAlign: "top" }}>
                                            <DropDownMenu className="language" value={this.state.selectedProgrammingLanguage} onChange={this.handleChangeProgrammingLanguage}>
                                                <MenuItem value={1} primaryText="JavaScript" />
                                                <MenuItem value={2} primaryText="Java" />
                                            </DropDownMenu>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style={{ width: "50%", padding: "6px" }}>
                                            <TextField
                                                floatingLabelText="Description"
                                                multiLine={true}
                                                rows={3}
                                                fullWidth={true}
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                        <td style={{ width: "50%", padding: "6px", verticalAlign: "bottom" }}>
                                            <TextField
                                                floatingLabelText="Youtube-Link"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                                fullWidth={true}
                                            />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </Paper>
                        <br />
                        <br />
                        <table style={{ width: "100%" }}>
                            <tbody>
                                <tr>
                                    <td style={{ width: "50%", padding: "6px", paddingLeft: "0px" }}>
                                        <h4>Pattern Solution</h4>
                                        <Paper zDepth={4}>
                                            <EditorAce mode='javascript'/>
                                        </Paper>
                                    </td>
                                    <td style={{ width: "50%", padding: "6px", paddingRight: "0px" }}>
                                        <h4>Template</h4>
                                        <Paper zDepth={4}>
                                            <EditorAce mode='javascript'/>
                                        </Paper>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <h4>Testcases</h4>
                        <Paper zDepth={2} className="paper" width="100%">
                            <table className="paper" width="100%">
                                <tbody>
                                    <tr>
                                        <td style={{ width: "33%", padding: "6px" }}>
                                            <TextField
                                                floatingLabelText="Case 1: Input"
                                                fullWidth={false}
                                                width="12%"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                        <td style={{ width: "33%", padding: "6px" }}>
                                            <TextField
                                                floatingLabelText="Case 2: Input"
                                                fullWidth={false}
                                                width="12%"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>

                                        <td style={{ width: "33%", padding: "6px" }}>
                                            <TextField
                                                floatingLabelText="Case 3: Input"
                                                fullWidth={false}
                                                width="12%"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton label="Create" onClick={handleClick} />
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default ExercisesTab;