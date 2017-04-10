import React from 'react';
import ReactDom from 'react-dom';
import Codemirror from 'react-codemirror';
//import '../../node_modules/codemirror/mode/javascript/javascript.js';
import '../../node_modules/codemirror/mode/clike/clike.js';



function getInitialState() {
    return {
        readOnly: false,
        mode: 'clike',
        mime: 'text/x-java'
    };
};

function updateCode(newCode) {
    this.setState({
        code: newCode
    });
};

function changeMode(e) {
    var mode = e.target.value;
    this.setState({
        mode: mode,
        code: defaults[mode]
    });
};

class Solution extends React.Component {
    render() {
        return (
            <div>
                <Codemirror ref="editor" onChange={this.updateCode} />
            </div>
        );
    }
}

export default Solution;