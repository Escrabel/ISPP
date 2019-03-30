app.service('BuyService', [
		'$http', function($http) {
			this.getBuysAdmin = function getBuysAdmin() {
				return $http({
					method : 'GET',
					url : 'buy/administrator/listRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

			this.getBuysTattooist = function getBuysTattooist() {
				return $http({
					method : 'GET',
					url : 'buy/tattooist/listRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

			this.getBuysCustomer = function getBuysCustomer() {
				return $http({
					method : 'GET',
					url : 'buy/customer/listRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

		}
]);

app.controller("BuyController", [
		'$scope', 'BuyService', function($scope, BuyService) {

			$scope.getBuys = function(rol) {
				if (rol == "admin") {
					$scope.getBuysAdmin();
				} else if (rol == "tattooist") {
					$scope.getBuysTattooist();
				} else if (rol == "customer") {
					$scope.getBuysCustomer();
				}
			};

			$scope.getBuysAdmin = function() {
				BuyService.getBuysAdmin().then(function(data) {
					$scope.fvar_buys = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_buys
					});
				});
			};

			$scope.getBuysTattooist = function() {
				BuyService.getBuysTattooist().then(function(data) {
					$scope.fvar_buys = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_buys
					});
				});
			};

			$scope.getBuysCustomer = function() {
				BuyService.getBuysCustomer().then(function(data) {
					$scope.fvar_buys = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_buys
					});
				});
			};
		}
]);
