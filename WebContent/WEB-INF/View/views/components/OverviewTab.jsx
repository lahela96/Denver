import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import { Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn }
    from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import IconButton from 'material-ui/IconButton';
import ActionCode from 'material-ui/svg-icons/action/code';
import ActionSearch from 'material-ui/svg-icons/action/search';


var lecturesjson;
const courselist=[];
const lecturelist=[];
const exerciselist=[];

const tableData = [
    {
        name: 'John Smith',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
    {
        name: 'Randal White',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Stephanie Sanders',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Steve Brown',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Joyce Whitten',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Samuel Roberts',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
    {
        name: 'Adam Moore',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
];


function handleClick(e) {
    fetch('http://localhost:8080/user', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'user',
            crud: '2',
            id: ""
        })
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
};



class OverviewTab extends React.Component {
    constructor() {
        super();
        this.state = {
            open: true,
            value: 1,
			selectedLecture: 0,
			selectedCourse:0,
			selectedExercise:0
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
			lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
			for(var j=1;j<=lecturesjson.course[0].lectures.length;j++){
				lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[0].lectures[j-1].lecture_title} />);
			}
		}
		
		/*if(exerciselist.length===0){
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'All Exercises'} />);
			for(var k=1;j<lecturesjson.course[0].lectures[0].exercises.length;k++){
				exerciselist.push(<MenuItem value={k} key={k} primaryText={lecturesjson.course[0].lectures[0].exercises[k].exercise_title} />);
			}
		}*/
		console.log(lecturesjson);	
	}
	
	handleChangeCourse(event, index, value){
		var countLectures = lecturelist.length;
		var countExercises = exerciselist.length;
		
		for(var i =0;i<countLectures;i++){
			lecturelist.pop();
		}
		for(var i =0;i<countExercises;i++){
			exerciselist.pop();
		}
		lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
		for(var j=1;j<=lecturesjson.course[value].lectures.length;j++){
			lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[value].lectures[j-1].lecture_title} />);
		}
		/*
		for(var k=0;j<lecturesjson.course[value].lectures[0].exercises.length;k++){
			lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[value].lectures[0].exercises[k].exercise_title} />);
		}
		*/
		
		this.setState({selectedCourse: value});
		this.setState({selectedLecture: 0});
		this.setState({selectedExercise: 0});
	}
	
	handleChangeLecture(event, index, value){
		var count = exerciselist.length;
		for(var i =0;i<count;i++){
			exerciselist.pop();
		}
		if(value!=0)
		{
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'All Exercises'} />);
			for(var k=1;k<=lecturesjson.course[this.state.selectedCourse].lectures[value-1].exercises.length;k++){
				exerciselist.push(<MenuItem value={k} key={k} primaryText={lecturesjson.course[this.state.selectedLecture].lectures[value-1].exercises[k-1].exercise_title} />);
			}
		}
		this.setState({selectedLecture: value});
		this.setState({selectedExercise: 0});
	}
	
	handleChangeExercise(event, index, value){
		this.setState({selectedExercise: value});
	}

    render() {
        return (
            <div>
                <Card>
                    <Divider />
                    <CardText className="loginbody">
                        <Paper zDepth={4}>
                            <Table selectable={false}
                            >
                                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                                    <TableRow>
                                        <TableHeaderColumn colSpan="6" style={{ textAlign: 'center', background: "#d1d1d1" }}>
                                            <DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse}>
												{courselist}
                                            </DropDownMenu>
                                            <DropDownMenu value={this.state.selectedLecture} onChange={this.handleChangeLecture}>
                                                {lecturelist}
                                            </DropDownMenu>
                                            <DropDownMenu value={this.state.selectedExercise} onChange={this.handleChangeExercise}>
                                                {exerciselist}
                                            </DropDownMenu>
                                            <IconButton onClick={handleClick}>
                                                    <ActionSearch/>
                                                </IconButton>
                                        </TableHeaderColumn>
                                    </TableRow>
                                    <TableRow style={{ background: "#d1d1d1" }}>
                                        <TableHeaderColumn>Course</TableHeaderColumn>
                                        <TableHeaderColumn>Lecture</TableHeaderColumn>
                                        <TableHeaderColumn>Exercise</TableHeaderColumn>
                                        <TableHeaderColumn>Name</TableHeaderColumn>
                                        <TableHeaderColumn>Status</TableHeaderColumn>
                                        <TableHeaderColumn>View Code</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody displayRowCheckbox={false} style={{ background: "#d1d1d1" }}>
                                    {tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{"WWI14SEA"}</TableRowColumn>
                                            <TableRowColumn>{row.lecture}</TableRowColumn>
                                            <TableRowColumn>{row.exercise}</TableRowColumn>
                                            <TableRowColumn>{row.name}</TableRowColumn>
                                            <TableRowColumn>{row.status}</TableRowColumn>
                                            <TableRowColumn>
                                                <IconButton>
                                                    <ActionCode/>
                                                </IconButton>
                                            </TableRowColumn>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default OverviewTab;