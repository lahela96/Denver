import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import NavBar from './components/NavBar.jsx';
import Solution from './components/Solution.jsx';
import { Tabs, Tab } from 'material-ui/Tabs';
import Slider from 'material-ui/Slider';
import ExercisesTab from './components/ExercisesTab.jsx';
import AssignExercisesTab from './components/AssignExercisesTab.jsx';
import OverviewTab from './components/OverviewTab.jsx';
import LecturesTab from './components/LecturesTab.jsx';
import DocentBar from './components/DocentBar.jsx';



export class Docent extends React.Component {
    render() {
        return (
            <div>
                <DocentBar/>
                <Content />
            </div>
        );
    }
}

class Content extends React.Component {
    render() {
        return (
            <div id="contentDocent" className="contentDocent">
                <MuiThemeProvider muiTheme={getMuiTheme({
                    datePicker: { selectColor: '#bd051f' },
                    flatButton: {primaryTextColor: '#bd051f'}
                }
                )} >
                    <div>
                        <Tabs>
                            <Tab label="New Exercise" style={{ 'backgroundColor': '#bd051f' }}>
                                <ExercisesTab />
                            </Tab>
                            <Tab label="Append Exercises" style={{ 'backgroundColor': '#bd051f' }}>
                                <AssignExercisesTab />
                            </Tab>
                            <Tab label="Overview" style={{ 'backgroundColor': '#bd051f' }}>
                                <OverviewTab />
                            </Tab>
                            <Tab
                                style={{ 'backgroundColor': '#bd051f' }}
                                label="New Lecture"
                                >
                                <LecturesTab />
                            </Tab>
                        </Tabs>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default Docent;