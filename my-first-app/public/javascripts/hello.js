if (window.console) {
  console.log("Welcome to your Play application's JavaScript!");
}

/**
 * Document Ready: Handler for .ready() called.
 */
$( document ).ready(function() {

  /**
   * User Combo box
   */
  $('#userId').selectize({
  	valueField: 'id',
  	labelField: 'login',
  	searchField: ['login', 'id'],
  	sortField: ['login', 'id'],
	create: false,
	allowEmptyOption: false,
	preload: false,
	openOnFocus: false,
  	render: {
			option: function(item, escape) {
				return '<div>' +
					'<span class="name">' + escape(item.id) + '</span> - ' +
					'<span class="description">' + escape(item.login) + '</span>'+
				'</div>';
			}
	},
	load: function(query, callback) {
		if (!query.length) {
			callback();
		} else {
			console.log("user search:"+query);
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
  
  /**
   * Item Combo box
   */
  $('#parentItemId').selectize({
  	valueField: 'id',
  	labelField: 'name',
  	searchField: ['name', 'id'],
  	sortField: ['name', 'id'],
	create: false,
	allowEmptyOption: false,
	preload: false,
	openOnFocus: false,
  	render: {
			option: function(item, escape) {
				return '<div>' +
					'<span class="name">' + escape(item.id) + '</span> - ' +
					'<span class="description">' + escape(item.name) + '</span>'+
				'</div>';
			}
	},
	load: function(query, callback) {
		if (!query.length) {
			callback();
		} else {
			console.log("item search:"+query);
			jsRoutes.controllers.JavaScript.searchItems(query).ajax( {
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