$(function(){
  $('#editor').wysiwyg({
    controls: {
      strikeThrough : { visible : true },
      underline     : { visible : true },

      justifyLeft   : { visible : true },
      justifyCenter : { visible : true },
      justifyRight  : { visible : true },
      justifyFull   : { visible : true },

      indent  : { visible : true },
      outdent : { visible : true },

      subscript   : { visible : true },
      superscript : { visible : true },

      undo : { visible : true },
      redo : { visible : true },

      insertOrderedList    : { visible : true },
      insertUnorderedList  : { visible : true },
      insertHorizontalRule : { visible : true },
      h1: {visible: false},
      h2: {visible: false},
      h4: {
              visible: true,
              className: 'h4',
              command: ($.browser.msie || $.browser.safari) ? 'formatBlock' : 'heading',
              arguments: ($.browser.msie || $.browser.safari) ? '<h4>' : 'h4',
              tags: ['h4'],
              tooltip: 'Header 4'
      },
      h5: {
              visible: true,
              className: 'h5',
              command: ($.browser.msie || $.browser.safari) ? 'formatBlock' : 'heading',
              arguments: ($.browser.msie || $.browser.safari) ? '<h5>' : 'h5',
              tags: ['h5'],
              tooltip: 'Header 5'
      },
      h6: {
              visible: true,
              className: 'h6',
              command: ($.browser.msie || $.browser.safari) ? 'formatBlock' : 'heading',
              arguments: ($.browser.msie || $.browser.safari) ? '<h6>' : 'h6',
              tags: ['h6'],
              tooltip: 'Header 6'
      },

      cut   : { visible : true },
      copy  : { visible : true },
      paste : { visible : true },
      html  : { visible: true }
    }
  });
});