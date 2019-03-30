app.service('MessageFolderService', [
		'$http', function($http) {

			this.getMessageFolders = function getMessageFoldersTattooist() {
				return $http({
					method : 'GET',
					url : 'messageFolder/actor/listRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};
		}
]);

app.controller("MessageFolderController", [
		'$scope', 'MessageFolderService', function($scope, MessageFolderService) {

			$scope.getMessageFolders = function() {
				MessageFolderService.getMessageFolders().then(function(data) {
					$scope.fvar_messageFolders = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_messageFolders
					});
				});
			};
		}
]);
