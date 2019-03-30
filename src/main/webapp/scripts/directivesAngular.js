app.directive("imgUpload", function($http, $compile) {
	return {
		restrict : 'AE',
		scope : {
			url : "@",
			method : "@",
		},
		template : '<input class="fileUpload" type="file" multiple />' + '<div style="width:40%;height:20%"class="dropzone">' + '<p class="msg">Upload image</p>' + '</div>'
				+ '<div class="preview clearfix">' + '<div class="previewData clearfix" ng-repeat="data in previewData track by $index">'
				+ '<img id="uploadImg" src={{data.src}}></img>' + '<div class="previewDetails">' + '<div class="detail"><b>Name : </b>{{data.name}}</div>'
				+ '<div class="detail"><b>Type : </b>{{data.type}}</div>' + '<div class="detail"><b>Size : </b> {{data.size}}</div>' + '</div>' + '<div class="previewControls">'
				+ '<span ng-hide="true" ng-click="upload(data)" class="circle upload">' + '<i class="fa fa-check"></i>' + '</span>'
				+ '<span ng-hide="true" ng-click="remove(data)" class="circle remove">' + '<i class="fa fa-close"></i>' + '</span>' + '</div>' + '</div>' + '</div>',
		link : function(scope, elem, attrs) {
			var formData = new FormData();
			scope.previewData = [];

			function previewFile(file) {
				var reader = new FileReader();
				var obj = new FormData().append('file', file);
				reader.onload = function(data) {
					var src = data.target.result;
					var size = ((file.size / (1024 * 1024)) > 1) ? (file.size / (1024 * 1024)) + ' mB' : (file.size / 1024) + ' kB';
					scope.$apply(function() {
						if (scope.previewData.length != 0) {
							scope.previewData.pop();
						}
						scope.previewData.push({
							'name' : file.name,
							'size' : size,
							'type' : file.type,
							'src' : src,
							'data' : obj,
							'modeloAngular' : scope.modeloAngular
						});
					});
					// console.log(scope.previewData);
				}
				reader.readAsDataURL(file);
			}

			function uploadFile(e, type) {
				e.preventDefault();
				var files = "";
				if (type == "formControl") {
					files = e.target.files;
				} else if (type === "drop") {
					files = e.originalEvent.dataTransfer.files;
				}
				for ( var i = 0; i < files.length; i++) {
					var file = files[i];
					if (file.type.indexOf("image") !== -1) {
						previewFile(file);
					} else {
						alert(file.name + " is not supported");
					}
				}
			}
			elem.find('.fileUpload').bind('change', function(e) {
				uploadFile(e, 'formControl');
			});

			elem.find('.dropzone').bind("click", function(e) {
				$compile(elem.find('.fileUpload'))(scope).trigger('click');
			});

			elem.find('.dropzone').bind("dragover", function(e) {
				e.preventDefault();
			});

			elem.find('.dropzone').bind("drop", function(e) {
				uploadFile(e, 'drop');
			});
			scope.upload = function(obj) {
				return null;
			}

			scope.remove = function(data) {
				var index = scope.previewData.indexOf(data);
				scope.previewData.splice(index, 1);
			}
		}
	}
});
