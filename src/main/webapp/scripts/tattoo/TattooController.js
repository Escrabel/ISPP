app.service('TattooService', [
		'$http', function($http) {
			this.getTattoosAll = function getTattoosAll() {
				return $http({
					method : 'GET',
					url : 'tattoo/listRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

			this.getTattoosBest8 = function getTattoosBest8() {
				return $http({
					method : 'GET',
					url : 'tattoo/listBest8Rest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

			this.getTattoo = function getTattoo(tattooId) {
				return $http({
					method : 'GET',
					url : 'tattoo/getRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

			this.getTattoosTattooist = function getTattoosTattooist() {
				return $http({
					method : 'GET',
					url : 'tattoo/tattooist/listRest.do',
					headers : 'Accept:application/json'
				}).then(function(response) {
					return response.data;
				});
			};

			this.getTattoosFilter = function getTattoosFilter(filter) {
				var filterCorregido = this.reconstruct(filter);
				return $http({
					method : 'GET',
					url : 'tattoo/listFilterRest.do',
					headers : 'Accept:application/json',
					params : {
						localization : filterCorregido.localization,
						precioOne : filterCorregido.precioOne,
						precioTwo : filterCorregido.precioTwo,
						dateOne : filterCorregido.dateOne,
						dateTwo : filterCorregido.dateTwo,
						name : filterCorregido.name,
						description : filterCorregido.description
					}
				}).then(function(response) {
					return response.data;
				});
			};

			this.reconstruct = function reconstruct(filter) {
				// Las fechas (si existen) nunca llegan al $scope de angular
				filter.dateOne = document.getElementById("from").value;
				filter.dateTwo = document.getElementById("to").value;
				// cuando no pongo fecha llega cadena vacia y falla en el servidor
				if (filter.dateOne == "") {
					filter.dateOne = null;
				}
				if (filter.dateTwo == "") {
					filter.dateTwo = null;
				}
				return filter;
			};

			this.getImgFromTattooId = function getImgFromTattooId(id) {
				return $http({
					method : 'GET',
					url : 'tattoo/imgFromTattooId.do',
					headers : 'Accept:application/json',
					params : {
						tattooId : id,
					}
				}).then(function(response) {
					return response.data;
				});
			};

		}
]);

app.controller("TattooController", [
		'$scope', 'TattooService', function($scope, TattooService) {

			$scope.getTattoo = function(tattooId) {
				TattooService.getTattoo(tattooId).then(function(data) {
					$scope.tattoo = data;
					console.log(data);

				});
			};
			$scope.initVariables = function() {
				$scope.filter = {
					"localization" : "",
					"precioOne" : null,
					"precioTwo" : null,
					"dateOne" : null,
					"dateTwo" : null,
					"name" : "",
					"description" : ""
				};
			};

			$scope.searchTattoos = function() {
				TattooService.getTattoosFilter($scope.filter).then(function(data) {
					$scope.fvar_tattoos = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_tattoos
					});
				});
			};

			$scope.insertImg = function() {
				// No me coge bien el archivo
				// var jquery = $('#archivo');
				// var archivo = jquery.prop('archivo');
				// document.getElementById("imgB64String").value = archivo;
				// console.log(document.getElementById("imgB64String").value);
				var src = document.getElementById("uploadImg").src;
				$scope.fvar_imgB64String = src;

			};

			$scope.getTattoos = function(rol, url) {
				if (rol == "tattooist") {
					if (url.includes("tattoo/list.do")) {
						$scope.getTattoosAll();
					} else {
						$scope.getTattoosTattooist();
					}
				} else {
					$scope.getTattoosAll();
				}
			};

			$scope.getTattoosAll = function() {
				TattooService.getTattoosAll().then(function(data) {
					$scope.fvar_tattoos = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_tattoos
					});
				});
			};

			$scope.getTattoosBest8 = function() {
				TattooService.getTattoosBest8().then(function(data) {
					$scope.fvar_tattoos = data;
					// No hace falta el tableParams porque no lo mostramos en tabla
				});
			};

			$scope.getTattoosTattooist = function() {
				TattooService.getTattoosTattooist().then(function(data) {
					$scope.fvar_tattoos = data;
					$scope.tableParams = new NgTableParams({}, {
						dataset : $scope.fvar_tattoos
					});
				});
			};

			// metodo para el list
			$scope.getImgFromTattooId = function(tattooId, iteracion) {
				TattooService.getImgFromTattooId(tattooId).then(function(data) {
					// Tengo que hacerlo con el class y no con el id porque
					// se tiene aplicado el css para las img con id=foto
					// asi que todas las img tinen que tener el id=foto
					var img = document.getElementsByClassName("foto" + iteracion)[0];
					img.src = data;
				});
			};

			// metodo para el view
			$scope.getImgFromTattooIdView = function(tattooId) {
				TattooService.getImgFromTattooId(tattooId).then(function(data) {
					$scope.imgString = data;
				});
			};

		}
]);
