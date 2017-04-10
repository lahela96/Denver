import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import RaisedButton from 'material-ui/RaisedButton';

var lecturesjson;
const courselist=[];
const lecturelist=[];

function handleChange(event, index, value) {
    this.setState({ value });
};

function handleClick(e) {
    fetch('http://localhost:8080/', {
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

class AssignExercisesTab extends React.Component {

    constructor() {
        super();
        this.state = {
            open: true,
            value: 1,
			selectedLecture: 0,
			selectedCourse:0
        };
		this.handleChangeLecture = this.handleChangeLecture.bind(this);
		this.handleChangeCourse = this.handleChangeCourse.bind(this);
    }
	
	componentWillMount() {
		var lectures='{"course": [	{"course_title":"WWI14SEA",	 "course_id":"1",	 "lectures":[		{"lecture_title":"Datenbanken",		"lecture_id": "0001",		"exercises": [			{ "exercise_title":"Fibonacci", "exercise_id":"0123123"},			{ "exercise_title":"Test2", "exercise_id":"0123124"},			{ "exercise_title":"Test3", "exercise_id":"0123125"}		]},		{"lecture_title":"Webprogrammierung",		"lecture_id": "0002",		"exercises": [			{ "exercise_title":"WEB1", "exercise_id":"0123123"},			{ "exercise_title":"WEB2", "exercise_id":"1123124"},			{ "exercise_title":"WEB3", "exercise_id":"1123125"}		]},		{"lecture_title":"Test2",		"lecture_id": "0003",		"exercises": [			{ "exercise_title":"Test1", "exercise_id":"2123123"},			{ "exercise_title":"Test2", "exercise_id":"2123124"},			{ "exercise_title":"Test3", "exercise_id":"2123125"}	]}	 ]},	{"course_title":"WWI14AMA",	 "course_id":"1",	 "lectures":[		{"lecture_title":"Datenbanken",		"lecture_id": "0001",		"exercises": [			{ "exercise_title":"Fibonacci", "exercise_id":"0123123"},			{ "exercise_title":"Test2", "exercise_id":"0123124"},			{ "exercise_title":"Test3", "exercise_id":"0123125"}		]},		{"lecture_title":"Webprogrammierung",		"lecture_id": "0002",		"exercises": [			{ "exercise_title":"WEB1", "exercise_id":"0123123"},			{ "exercise_title":"WEB2", "exercise_id":"1123124"},			{ "exercise_title":"WEB3", "exercise_id":"1123125"}		]},		{"lecture_title":"Test",		"lecture_id": "0003",		"exercises": [			{ "exercise_title":"Test1", "exercise_id":"2123123"},			{ "exercise_title":"Test2", "exercise_id":"2123124"},			{ "exercise_title":"Test3", "exercise_id":"2123125"}		]}	 ]}   ]}';
		lecturesjson= JSON.parse(lectures);
		
		if(courselist.length===0){
			for(var i=0;i<lecturesjson.course.length;i++){
				courselist.push(<MenuItem value={i} key={i} primaryText={lecturesjson.course[i].course_title} />);
			}
		}
		if(lecturelist.length===0){
			for(var j=0;j<lecturesjson.course[0].lectures.length;j++){
				lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[0].lectures[j].lecture_title} />);
			}
		}
		console.log(lecturesjson);	
	}
	
	handleChangeCourse(event, index, value){
		var count = lecturelist.length;
		for(var i =0;i<count;i++){
			lecturelist.pop();
		}
		for(var j=0;j<lecturesjson.course[value].lectures.length;j++){
			lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[value].lectures[j].lecture_title} />);
		}
		
		this.setState({selectedLecture: 0});
		this.setState({selectedCourse: value});
	}
	
	handleChangeLecture(event, index, value){
		this.setState({selectedLecture: value});	
	}
    render() {
        return (
            <div>
                <Card>
                    <Divider />
                    <CardText className="loginbody">
                        <h4>Step 1: Select Exercise(s)</h4>
                        <Paper zDepth={2} className="paper">
                            <div>
                                <Table multiSelectable={true} className="paper" >
                                    <TableHeader>
                                        <TableRow className="paper">
                                            <TableHeaderColumn>Title</TableHeaderColumn>
                                            <TableHeaderColumn>Language</TableHeaderColumn>
                                            <TableHeaderColumn>Creation Date</TableHeaderColumn>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody deselectOnClickaway={false} className="paper">
                                        <TableRow>
                                            <TableRowColumn>Fibonacci</TableRowColumn>
                                            <TableRowColumn>Java</TableRowColumn>
                                            <TableRowColumn>24.11.2011</TableRowColumn>
                                        </TableRow>
                                        <TableRow>
                                            <TableRowColumn>Division</TableRowColumn>
                                            <TableRowColumn>JavaScript</TableRowColumn>
                                            <TableRowColumn>24.11.2011</TableRowColumn>
                                        </TableRow>
                                        <TableRow>
                                            <TableRowColumn>Sum</TableRowColumn>
                                            <TableRowColumn>JavaScript</TableRowColumn>
                                            <TableRowColumn>24.11.2011</TableRowColumn>
                                        </TableRow>
                                        <TableRow>
                                            <TableRowColumn>Sub</TableRowColumn>
                                            <TableRowColumn>Java</TableRowColumn>
                                            <TableRowColumn>24.11.2011</TableRowColumn>
                                        </TableRow>
                                    </TableBody>
                                </Table>
                            </div>
                        </Paper>

                        <br />
                        <h4>Step 2: Select Lecture</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse}>
                                {courselist}
                            </DropDownMenu>

                            <DropDownMenu value={this.state.selectedLecture} onChange={this.handleChangeLecture}>
                                {lecturelist}
                            </DropDownMenu>
                        </Paper>

                        <br />
                        <h4>Step 3: Select Deadline</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <DatePicker floatingLabelText="Deadline" mode="landscape"/>
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton label="Assign"onClick={handleClick} />
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default AssignExercisesTab;