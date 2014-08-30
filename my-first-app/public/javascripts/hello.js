if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

$( document ).ready(function() {
  // Handler for .ready() called.

  $('#userId').selectize({
  	valueField: 'id',
  	labelField: 'login',
  	searchField: ['login', 'id'],
  	sortField: ['login', 'id'],
	create: false,
	preload: true,
  	render: {
			option: function(item, escape) {
				return '<div>' +
					'<span class="name">' + escape(item.id) + '</span> - ' +
					'<span class="description">' + escape(item.login) + '</span>'+
				'</div>';
			}
	},
	onInitialize: function() {
		// Update the default selected value
		var defaulSelectedUser = $("#userId").val();
		var userSelectize = this;
		jsRoutes.controllers.JavaScript.getUser(defaulSelectedUser).ajax( {
			error: function() {
			},
			success: function(res) {
				console.log(res);
				// Update the comboBox data
		        var data   = $.extend({}, userSelectize.options[defaulSelectedUser], {
		            login: res.login
		        });
				userSelectize.updateOption(defaulSelectedUser, data);
			}
		});
	},
	load: function(query, callback) {
		if (!query.length) return callback();
		jsRoutes.controllers.JavaScript.searchUsers(query).ajax( {
			error: function() {
				callback();
			},
			success: function(res) {
				console.log(res);
				callback(res);
			}
		});
	}
  });
  
  
});	