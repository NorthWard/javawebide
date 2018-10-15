<!DOCTYPE html>
<html lang="en">
<script src="/webjars/jquery/jquery.js"></script>
<!--begin code mirror -->
<!--下面两个是使用Code Mirror必须引入的-->
<link rel="stylesheet" href="/webjars/codemirror/lib/codemirror.css"/>
<script src="/webjars/codemirror/lib/codemirror.js"></script>


<!--引入css文件，用以支持主题-->
<link rel="stylesheet" href="/webjars/codemirror/theme/dracula.css"/>
<link rel="stylesheet" href="/webjars/codemirror/addon/dialog/dialog.css"/>

<!--支持代码折叠-->
<link rel="stylesheet" href="/webjars/codemirror/addon/fold/foldgutter.css"/>
<link rel="stylesheet" href="/webjars/codemirror/addon/hint/show-hint.css"/>

<!--Java代码高亮必须引入-->
<script src="/webjars/codemirror/mode/clike/clike.js"></script>
<!--groovy代码高亮-->
<script src="/webjars/codemirror/mode/groovy/groovy.js"></script>
<script src="/webjars/codemirror/addon/dialog/dialog.js"></script>
<script src="/webjars/codemirror/addon/fold/foldcode.js"></script>
<script src="/webjars/codemirror/addon/fold/foldgutter.js"></script>
<script src="/webjars/codemirror/addon/fold/brace-fold.js"></script>
<script src="/webjars/codemirror/addon/fold/comment-fold.js"></script>
<script src="/webjars/codemirror/addon/hint/show-hint.js"></script>
<!--括号匹配-->
<script src="/webjars/codemirror/addon/edit/matchbrackets.js"></script>
<!--end Code Mirror -->

<head>
    <meta charset="utf-8"/>
    <title>代码框</title>
</head>
<body>
<!-- begin code -->
<textarea class="form-control" id="code" name="code"></textarea>
<!-- end code-->
</div>

<script>
    //根据DOM元素的id构造出一个编辑器
    var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        mode: "text/groovy",    //实现groovy代码高亮
        mode: "text/x-java", //实现Java代码高亮
        lineNumbers: true,	//显示行号
        theme: "dracula",	//设置主题
        lineWrapping: true,	//代码折叠
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
        matchBrackets: true,	//括号匹配
        //readOnly: true,        //只读
    });
    // editor.setSize('800px', '950px');     //设置代码框的长宽

    editor.setValue("");    //先代码框的值清空
    // editor.setValue(obj.scriptCode);    //给代码框赋值

    // editor.setOption("readOnly", true);

    editor.on("change", function(instance,changeObj){
        $.ajax({
            type: 'GET',
             url: '/api/getHints?word=' + changeObj,
            success: function(resp) {
                var options = {
                    hint: function() {
                        return {
                            from: editor.getDoc().getCursor(),
                            to: editor.getDoc().getCursor(),
                            list: resp
                        }
                    }
                };
                editor.showHint(options);
            },
            error: function(error) {
                editor.openNotification(error, options)
            }
        });




    });



</script>
</body>
</html>