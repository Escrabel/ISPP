app.service('MessageService', [
		'$http', function($http) {

			this.getMessages = function getMessagesTattooist(messageFolderId) {
				return $http({
					method : 'GET',
					url : 'message/actor/listRest.do',
					headers : 'Accept:application/json',
					params : {
						messageFolderId : messageFolderId
					}
				}).then(function(response) {
					return response.data;
				});
			};

			this.seeMessage = function seeMessage(messageId) {
				return $http({
					method : 'GET',
					url : 'message/actor/details.do',
					headers : 'Accept:application/json',
					params : {
						messageId : messageId
					}
				}).then(function(response) {
					return response.data;
				});
			};
		}
]);

app.controller("MessageController", [
		'$scope', 'MessageService', function($scope, MessageService) {

			$scope.getMessages = function(messageFolderId) {
				MessageService.getMessages(messageFolderId).then(function(data) {
					$scope.fvar_messages = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_messages
					});
				});
			};

			$scope.seeMessage = function(messageId) {
				MessageService.seeMessage(messageId).then(function(data) {
					// Esta funcion aun no redirige correctamente
				});
			};
		}
]);
