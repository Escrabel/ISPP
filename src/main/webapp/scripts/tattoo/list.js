app.service('TattooService', [
		'$http', function($http) {
			// this.getTattoos = function getTattoos() {
			// return $http({
			// method : 'GET',
			// url : 'validacion/rest/list.do',
			// headers : 'Accept:application/json'
			// }).then(function(response) {
			// return response.data;
			// });
			// };

		}
]);

app.controller("TattooController", [
		'$scope', 'TattooService', function($scope, TattooService) {
			$scope.insertImg = function() {
				var src = document.getElementById("uploadImg").src;
				$scope.fvar_imgB64String = src;
				console.log($scope.fvar_imgB64String);

			};

		}
]);
