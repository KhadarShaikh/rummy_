resourceApp.factory('masterService', [
		"$http",
		"$q",
		"APIURL",
		function($http, $q, APIURL) {

			return {

				joinwithinlist : function(pageIndex, pageSizeSelected) { // pageIndex,
					// pageSizeSelected
					var deferred = $q.defer();
					$http.get(
							'rest/joinWithIn/listJoinWithIn/' + pageIndex + "/"
									+ pageSizeSelected).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},

				createnewJoin : function(newJoin1) {
					var deferred = $q.defer();
					$http.post('rest/joinWithIn/createjoinWithIn', newJoin1)
							.success(function(response) {
								deferred.resolve(response);
							}).error(function(err) {
								deferred.reject(err);
							})
					return deferred.promise;
				},

				joinwithingetById : function(id) {
					var deferred = $q.defer();
					$http.get('rest/joinWithIn/findOneByPrimaryId/' + id)
							.success(function(response) {
								deferred.resolve(response);
							}).error(function(err) {
								deferred.reject(err);
							})
					return deferred.promise;
				},

				updateJoin : function(joinWithIn1) {
					var deferred = $q.defer();
					$http.put(
							'rest/joinWithIn/updatejoinWithIn/'
									+ joinWithIn1._id, joinWithIn1).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},
				jobCategoryget : function(pageIndex, pageSizeSelected) { // pageindex/selected
					debugger;
					var deferred = $q.defer();
					$http.get(
							'rest/jobCategory/listJobCategory/' + pageIndex
									+ "/" + pageSizeSelected).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},

				jobCategorypost : function(postcategory1) {
					var deferred = $q.defer();
					$http.post('rest/jobCategory/createJobCategory',
							postcategory1).success(function(response) {
						deferred.resolve(response);
					}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},
				jobCategoryById : function(id) {
					var deferred = $q.defer();
					$http.get('rest/jobCategory/findOneByPrimaryId/' + id)
							.success(function(response) {
								deferred.resolve(response);
							}).error(function(err) {
								deferred.reject(err);
							})
					return deferred.promise;
				},
				jobCategoryPut : function(putdata) {
					var deferred = $q.defer();
					$http
							.put(
									'rest/jobCategory/updateJobCategory/'
											+ putdata._id, putdata).success(
									function(response) {
										deferred.resolve(response);
									}).error(function(err) {
								deferred.reject(err);
							})
					return deferred.promise;
				},
				/*
				 * getmasterdetails: function(){ debugger; var deferred =
				 * $q.defer();
				 * $http.get('rest/requirement/addRequirement').success(function(response){
				 * deferred.resolve(response); }).error(function(err){
				 * deferred.reject(err); }) return deferred.promise; },
				 */
				newjobtype : function(pageIndex, pageSizeSelected) { // pageIndex,pageSizeSelected
					debugger;
					var deferred = $q.defer();
					$http.get(
							'rest/jobType/listJobType/' + pageIndex + "/"
									+ pageSizeSelected).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},

				saveNewJobtype : function(alluser) {
					var deferred = $q.defer();
					$http.post('rest/jobType/createJobType', alluser).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},

				getJobtypeById : function(id) {
					var deferred = $q.defer();
					$http.get('rest/jobType/findOneByPrimaryId/' + id).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					});
					return deferred.promise;
				},
				updateJobtype : function(putjobtype) {
					var deferred = $q.defer();
					$http.put('rest/jobType/updateJobType/' + putjobtype._id,
							putjobtype).success(function(response) {
						deferred.resolve(response);
					}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},
				JobtypedeleteRow : function(id){
			 		  debugger;
			 		  var deffered = $q.defer();
			 		  $http.delete('rest/jobType/deleteJobType/'+id).success(function(response){
			 			  deffered.resolve(response);
			 		  }).error(function(err){
			 			  deffered.reject(err);
			 		  })
			 		  return deffered.promise;
			 	  },
				allprimaryskills : function(pageIndex, pageSizeSelected) { // pageIndex,
					// pageSizeSelected
					var deferred = $q.defer();
					$http.get(
							'rest/primarySkills/listPrimarySkills/' + pageIndex
									+ "/" + pageSizeSelected).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},
				saveprimaryskills : function(alluser) {
					var deferred = $q.defer();
					$http.post('rest/primarySkills/createPrimarySkills',
							alluser).success(function(response) {
						deferred.resolve(response);
					}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},

				getPrimaryskillsById : function(id) {
					var deferred = $q.defer();
					$http.get('rest/primarySkills/findOneByPrimaryId/' + id)
							.success(function(response) {
								deferred.resolve(response);
							}).error(function(err) {
								deferred.reject(err);
							});
					return deferred.promise;
				},

				updatePrimaryskills : function(primary) {
					var deferred = $q.defer();
					$http.put(
							'rest/primarySkills/updatePrimarySkills/'
									+ primary._id, primary).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},
				secondaryget : function(pageIndex, pageSizeSelected) { // pageIndex,
					// pageSizeSelected
					debugger;
					var deferred = $q.defer();
					$http.get(
							'rest/secondarySkills/listSecondarySkills/'
									+ pageIndex + "/" + pageSizeSelected)
							.success(function(response) {
								deferred.resolve(response);
							}).error(function(err) {
								deferred.reject(err);
							})
					return deferred.promise;
				},
				secondaryadd : function(addskill) {
					var deferred = $q.defer();
					$http.post('rest/secondarySkills/createSecondarySkills',
							addskill).success(function(response) {
						deferred.resolve(response);
					}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				},
				secondaryById : function(id) {
					var deferred = $q.defer();
					$http.get('rest/secondarySkills/findOneByPrimaryId/' + id)
							.success(function(response) {
								deferred.resolve(response);
							}).error(function(err) {
								deferred.reject(err);
							})
					return deferred.promise;
				},
				secondaryedit : function(dataput1) {
					var deferred = $q.defer();
					$http.put(
							'rest/secondarySkills/updateSecondarySkill/'
									+ dataput1._id, dataput1).success(
							function(response) {
								deferred.resolve(response);
							}).error(function(err) {
						deferred.reject(err);
					})
					return deferred.promise;
				}

			}
		} ])
