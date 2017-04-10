import React from 'react';
import { render } from 'react-dom';
import AceEditor from 'react-ace';
import brace from 'brace';

import 'brace/mode/java';
import 'brace/mode/javascript';

import 'brace/theme/github';
import 'brace/theme/chrome';
import 'brace/theme/monokai';
import 'brace/theme/solarized_light';
import 'brace/ext/language_tools';


class EditorAce extends React.Component {
    render() {
        return (
            <AceEditor
                mode='javascript'
                theme="chrome"
                name="code"
                width="100%"
                minLines={16}
                maxLines={16}
                ref="ace"
                fontSize={16}
                editorProps={{ $blockScrolling: Infinity }}
                onLoad={(editor) => {
                    editor.getSession().setUseWrapMode(true);
                }}
            />
        );
    }
}

export default EditorAce;
