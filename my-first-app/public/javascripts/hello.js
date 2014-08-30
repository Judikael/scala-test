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
	allowEmptyOption: false,
	preload: false,
	openOnFocus: false,
	onDelete: function(values) {
		// Display all options
		var userSelectize = this;
		jsRoutes.controllers.JavaScript.listUsers().ajax( {
			error: function() {
			},
			success: function(res) {
				console.log(res);
				res.forEach(function(entry) {
				    userSelectize.addOption(entry);
				});
				userSelectize.refreshOptions(true);
			}
		});
	},
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
		if (!query.length) {
			callback();
		} else {
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
	}
  });
  
  
});	