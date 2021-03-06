resourceApp
              .factory(
                            'RAService',
                            [
                                          '$http',
                                          '$q',
                                          'APIURL',
                                          function($http, $q, APIURL) {
                                                 return {
                                                        RAReg : function(pageIndex, pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                               $http.get( 'rest/registration/listRegistrations/' + pageIndex + "/" + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        // Requirement vs Skills Service
                                                        getRequirementvsSkills : function() {
                                                             
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get('rest/requirement/requirementsByTopTechnologies')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // End of the service

                                                        // Availability Service
                                                        getAvailability : function() {
                                                              
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get('https://api.myjson.com/bins/ntc4y')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // End of the service

                                                        // Resource Vs Skills service
                                                        getResourcevsSkills : function() {
                                                        
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get('rest/resource/resourceCountByJobCategory')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // end of the service
                                                       /* getTopVendorslist : function() {
                                                              
                                                               var deferred = $q.defer();
                                                               $http.get('rest/resource/topVendors/1/10')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },*/
                                                        companieslist1 : function() {
                                                        
                                                               var deferred = $q.defer();
                                                               $http.get('rest/registration/listVendors')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {

                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        getsidefilterslist : function() {
                                                        	var regId = localStorage.getItem('registrationId');                                                     
                                                               var deferred = $q.defer();
                                                               $http.get('rest/resource/getAllResources/'+regId)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {

                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        	
                                                        getsidefilterslist_customer:function(regId){
                                                        	var regId = localStorage.getItem('registrationId');                                                     
                                                            var deferred = $q.defer();
                                                            $http.get('rest/requirement/getAllRequirements/'+regId)
                                                                          .success(function(response) {
                                                                        	  
                                                                                 deferred.resolve(response);
                                                                          }).error(function(err) {

                                                                                 deferred.reject(err);
                                                                          })
                                                            return deferred.promise;
                                                        },
                                                        
/*
								                         getsidefiltersAllResource_customer:function(){
								                        	 debugger;
                                                        	//var regId = localStorage.getItem('registrationId');                                                     
                                                            var deferred = $q.defer();
                                                            $http.get('rest/resource/getAllResources')
                                                                          .success(function(response) {
                                                                                 deferred.resolve(response);
                                                                          }).error(function(err) {

                                                                                 deferred.reject(err);
                                                                          })
                                                            return deferred.promise;
                                                        },
                                                      */  
                                                        getsidefilters_allrequirements : function() {      
                                                        
                                                               var deferred = $q.defer();
                                                               $http.get('rest/requirement/getAllRequirements')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {

                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        
                                                       
                                                       
                                                        
                                                        companieslist_customers:function(){
                                                        
                                                            var deferred = $q.defer();
                                                            $http.get('rest/registration/listCustomers')
                                                                          .success(function(response) {
                                                                                 deferred.resolve(response);
                                                                          }).error(function(err) {

                                                                                 deferred.reject(err);
                                                                          })
                                                            return deferred.promise;
                                                        	
                                                        },
                                                        /*getTopCustomerslist : function() {
                                                            
                                                               var deferred = $q.defer();
                                                               $http.get('rest/requirement/topCustomers/1/10')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },*/
                                                        saveRegistration : function(registration) {
                                                            
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .post('rest/registration/createRegistration',registration)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        buynow : function(pageIndex, pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                              
                                                               $http.get(
                                                                             'rest/plans/listPlans/' + pageIndex
                                                                                           + "/" + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        vendorbuynow : function() {
                                                               var deferred = $q.defer();
                                                            
                                                               $http.get('rest/plans/listPlans/1/10')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        getRegistrationById : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/registration/findOneByPrimaryId/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               });
                                                               return deferred.promise;
                                                        },
                                                        updateRegistration : function(registration) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/registration/saveRegistration/'
                                                                                           + registration._id,
                                                                             registration).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        MenuListnew : function() {
                                                               var deferred = $q.defer();
                                                               $http.get('rest/menu/listMenus/1/10')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        registrationStatus : function(registration) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/registration/inactiveOrActive/'
                                                                                           + registration._id,
                                                                             registration).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        Getfile : function(params) {
                                                          
                                                               console.log("RUNNING");
                                                               var deferred = $q.defer();

                                                               $http(
                                                                             {
                                                                                    url : ('rest/resource/downloadFile/' + params),
                                                                                    method : "GET",// you can use
                                                                                                                // also GET or
                                                                                                                // POST
                                                                                    data : params,
                                                                                    headers : {
                                                                                           'Content-type' : 'application/json'
                                                                                    },
                                                                                    responseType : 'arraybuffer',// THIS
                                                                                                                                            // IS
                                                                                                                                            // IMPORTANT
                                                                             }).success(function(data) {
                                                                      console.debug("SUCCESS");
                                                                      deferred.resolve(data);
                                                               }).error(function(data) {
                                                                      console.error("ERROR");
                                                                      deferred.reject(data);
                                                               });

                                                               return deferred.promise;
                                                        },

                                                        GetPlanById : function(registrationId) {
                                                
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/planMapping/findPlanMappingByRegId/'
                                                                                           + registrationId).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        changeplan : function(planId) {
                                                               
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           APIURL
                                                                                                         + '/ResourceAdda/rest/plans/getplansByPrimaryId/'
                                                                                                         + planId)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        paymentcarddetails : function(card) {
                                                        
                                                               var deferred = $q.defer();
                                                               $http.post('rest/payment/createPayment',
                                                                             card).success(function(response) {
                                                                      deferred.resolve(response);
                                                               }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        GetPreviousplan : function(planId) {
                                                         
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/plans/getPreviousPlansByRegId/'
                                                                                           + planId + '/1/5')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                    /*    postareqList : function(pageIndex,
                                                                      pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                               var local = localStorage
                                                                             .getItem("registrationId");
                                                               $http.get(
                                                                             'rest/requirement/findSowRequirementsByRegId/'
                                                                                           + local + "/" + pageIndex
                                                                                           + "/" + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },*/
                                                        postReqfilter : function(filter, value) {
                                                               if (filter.consultant == 0) {
                                                                      var deferred = $q.defer();
                                                                                    $http
                                                                                                  .get(
                                                                                                                'rest/requirement/listRequirements/1/5')
                                                                                                  .then(
                                                                                                                function(
                                                                                                                              response) {
                                                                                                                       deferred
                                                                                                                                     .resolve(response);
                                                                                                                }), function(
                                                                                                  err) {
                                                                                           deferred.reject(err);
                                                                                    }
                                                                      return deferred.promise;

                                                               } else {
                                                                      var deferred = $q.defer();
                                                                      $http.get(
                                                                                    "rest/requirement/findOneAllByCondition/"
                                                                                                  + filter.jobCategory
                                                                                                  + '/'
                                                                                                  + filter.consultant
                                                                                                  + '/1/5').then(
                                                                                    function(response) {
                                                                                           deferred.resolve(response);
                                                                                    }), function(err) {
                                                                             deferred.reject(err);
                                                                      }
                                                                      return deferred.promise;
                                                               }

                                                        },
                                                        uploadResumeToUrl : function(uploadFile,
                                                                      uploadUrl) {
                                                             
                                                               var deferred = $q.defer();
                                                               var fd = new FormData();
                                                               fd.append('uploadFile', uploadFile);
                                                               $http.put(uploadUrl, fd, {
                                                                      transformRequest : angular.identity,
                                                                      headers : {
                                                                             'Content-Type' : undefined
                                                                      }
                                                               }).success(function(response) {
                                                                      deferred.resolve(response);
                                                               }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        getCompanyList : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/registration/listRegistrations')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        adddata : function(postrequirement) {
                                                            
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .post(
                                                                                           'rest/requirement/createRequirement',
                                                                                           postrequirement)
                                                                             .success(function(response) {
                                                                                    console.log("success");
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        postareqGetById : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/requirement/findOneByPrimaryId/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        uploadFileToUrl : function(uploadFile,
                                                                      uploadUrl) {
                                                        
                                                               var deferred = $q.defer();
                                                               var fd = new FormData();
                                                               fd.append('uploadFile', uploadFile);
                                                               $http.put(uploadUrl, fd, {
                                                                      transformRequest : angular.identity,
                                                                      headers : {
                                                                             'Content-Type' : undefined
                                                                      }
                                                               }).success(function(response) {
                                                                      deferred.resolve(response);
                                                               }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        updatepostareq : function(postrequirement) {
                                                              
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .put(
                                                                                           'rest/requirement/saveRequirement/',
                                                                                           postrequirement)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        requirementStatus : function(postrequirement) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/requirement/inactiveOrActive/'
                                                                                           + postrequirement._id,
                                                                             postrequirement).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        registergetcontact : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/address/listAddresses/1/10')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        registeraddcontact : function(addcontact) {
                                                               var deferred = $q.defer();
                                                               $http.post('rest/address/createAddress',
                                                                             addcontact).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        registergetbyid : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/address/findOneByPrimaryId/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        userlist : function(pageIndex, pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/user/listUsers/' + pageIndex
                                                                                           + "/" + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        allusergetroles : function() {
                                                               var deferred = $q.defer();
                                                               $http.get('rest/user/getAllRoles/1/10')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        alluseradd : function(alluser) {
                                                               var deferred = $q.defer();
                                                               $http.post('rest/user/saveUser', alluser)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        allusergetbyid : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/user/getOneByPrimaryKey/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        alluserupdate : function(alluser) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/user/updateUser/'
                                                                                           + alluser._id, alluser)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        addresourcelist : function(pageIndex,
                                                                      pageSizeSelected) {
                                                        
                                                               var deferred = $q.defer();
                                                               var local = localStorage
                                                                             .getItem("registrationId");
                                                               $http.get(
                                                                           //  'rest/resource/findSowResourcesByRegId/'
                                                                                //           + local + "/" + pageIndex
                                                                                   //        + "/" + pageSizeSelected)
                                                                                           
                                                                              'rest/resource/listResources/'+ pageIndex
                                                                              + "/" + pageSizeSelected)            
                                                                                           
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        uploadResumeToUrl : function(uploadFile,
                                                                      uploadUrl) {
                                                           
                                                               var deferred = $q.defer();
                                                               var fd = new FormData();
                                                               fd.append('uploadFile', uploadFile);
                                                               $http.put(uploadUrl, fd, {
                                                                      transformRequest : angular.identity,
                                                                      headers : {
                                                                             'Content-Type' : undefined
                                                                      }
                                                               }).success(function(response) {
                                                                      deferred.resolve(response);
                                                               }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        datafilter : function(filter, value) {
                                                               if (filter.consultant == 0) {
                                                                      var deferred = $q.defer();
                                                                                    $http
                                                                                                  .get(
                                                                                                                'rest/resource/listResources/1/5')
                                                                                                  .then(
                                                                                                                function(
                                                                                                                              response) {
                                                                                                                       deferred
                                                                                                                                     .resolve(response);
                                                                                                                }), function(
                                                                                                  err) {
                                                                                           deferred.reject(err);
                                                                                    }
                                                                      return deferred.promise;
                                                               } else {
                                                                      var deferred = $q.defer();
                                                                                    $http
                                                                                                  .get(
                                                                                                                'rest/resource/findOneAllByCondition/'
                                                                                                                              + filter.totalExperience
                                                                                                                              + '/'
                                                                                                                              + filter.consultant
                                                                                                                              + '/1/5')
                                                                                                  .then(
                                                                                                                function(
                                                                                                                              response) {
                                                                                                                       deferred
                                                                                                                                     .resolve(response);
                                                                                                                }), function(
                                                                                                  err) {
                                                                                           deferred.reject(err);
                                                                                    }
                                                                      return deferred.promise;
                                                               }

                                                        },

                                                        RAresourcelist : function() {
                                                       
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/resource/listResources/1/5')
                                                                             .then(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }), function(err) {
                                                                      deferred.reject(err);
                                                               }
                                                               return deferred.promise;
                                                        },

                                                        addresource : function(resource) {
                                                           
                                                               var deferred = $q.defer();
                                                               $http.post('rest/resource/createResource',resource).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;

                                                        },
                                                        resourcegetById : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/resource/findOneByPrimaryId/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        updateresource : function(resource) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/resource/saveResource/'
                                                                                           + resource._id, resource)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        resourceStatus : function(resource) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/resource/inactiveOrActive/'
                                                                                           + resource._id, resource)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        postresourceById : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/resource/findResourcesByRegistrationId/'
                                                                                           + id + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        postresourceMapping : function(postresource) {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .post(
                                                                                           'rest/resourceMapping/createResourceMapping',
                                                                                           postresource)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        mappingresource : function(filter) {
                                                            
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/resource/findResourcesByTwoIds/'
                                                                                           + filter.selecttype + '/'
                                                                                           + filter.nameId + '/'
                                                                                           + filter.localId + '/1/5')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        PostresourceSoft : function(resource) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/resource/softLock/'
                                                                                           + resource._id, resource)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        PostresourceHard : function(resource) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/resource/hardLock/'
                                                                                           + resource._id, resource)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        addplan : function(plan) {

                                                               var deferred = $q.defer();
                                                               $http.post('rest/plans/createPlans', plan)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;

                                                        },
                                                        plangetById : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/plans/getplansByPrimaryId/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        planupdate : function(plan) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/plans/updatePlans/'
                                                                                           + plan._id, plan).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        planStatus : function(plan) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             'rest/plans/inactiveOrActive/'
                                                                                           + plan._id, plan).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        userStatus : function(user) {
                                                               var deferred = $q.defer();
                                                               $http.put(
                                                                             '/rest/user/inactiveOrActive/'
                                                                                           + userId, user).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        planlist : function() {
                                                               var deferred = $q.defer();

                                                               $http
                                                                             .get(
                                                                                           'rest/plans/listActivePlans/1/5')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // RA dashboard start
                                                        // Total resources by technology

                                                        RAtotalResourceByTechnology : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/resource/totalResourceByTechnology/')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        // Top customers by requirements
                                                        RAToptencustomers : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'https://api.myjson.com/bins/avtqg')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // Top Vendors By Resource

                                                        RAToptenVendors : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'https://api.myjson.com/bins/70iaw')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // Total resources by technology in cities
                                                        //
                                                        Ratencities : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/resource/resourcesByCityAndTechnology')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // requirement by technology

                                                   RArequirementTechnology : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/requirement/requirementsByTopTechnologies')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // requirement by Cities

                                                      /*  RArequirementCities : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/requirement/requirementsByJobLocation')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },*/

                                                        // top Technologies

                                                        RATopTech : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'https://api.myjson.com/bins/sh0hk')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        // RA dashboard end

                                                        // updatepostareq:function(postId,postrequirement){
                                                        // debugger;
                                                        // var deferred = $q.defer();
                                                        // $http.put(APIURL +
                                                        // "/ResourceAdda/rest/requirement/saveRequirement/"
                                                        // +postId,postrequirement).success(function(response){
                                                        // deferred.resolve(response);
                                                        // }).error(function(err){
                                                        // deferred.reject(err);
                                                        // })
                                                        // return deferred.promise;
                                                        // },
                                                        // vendor services
                                                        venpostareqList : function(pageIndex,pageSizeSelected) {
                                                        
                                                        	var deferred = $q.defer();
                                                           //    var local = localStorage.getItem("registrationId");
                                                               $http.get('rest/requirement/listRequirements/' + pageIndex + '/' + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        
                                                       
                                                        
                                                        vendoruserlist : function(pageIndex,
                                                                      pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                               var local = localStorage
                                                                             .getItem("registrationId");
                                                               $http.get(
                                                                             'rest/user/findAllByRegistrationId/'
                                                                                           + local + '/' + pageIndex
                                                                                           + '/' + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        search : function(pageIndex, search1,
                                                                      pageSizeSelected) {
                                                               var deferred = $q.defer();

                                                               var local = localStorage
                                                                             .getItem("registrationId");
                                                               $http.get(
                                                                             'rest/user/findUserByCondition/'
                                                                                           + local + '/' + search1
                                                                                           + '/' + pageIndex + '/'
                                                                                           + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        vendoraddresourcelist : function(pageIndex,
                                                                      pageSizeSelected) {
                                                        	
                                                               var deferred = $q.defer();
                                                               var local = localStorage
                                                                             .getItem("registrationId");
                                                               $http.get(
                                                                             'rest/resource/findResourcesByRegistrationId/'
                                                                                           + local + '/' + pageIndex
                                                                                           + '/' + pageSizeSelected)  
                                                                                           
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        vendorgetRegistrationById : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/registration/findOneByPrimaryId/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        
                                                        getmasterdetails: function(){
                                                     
                                                            var deferred = $q.defer();
                                                            $http.get('rest/requirement/addRequirement').success(function(response){
                                                                   deferred.resolve(response);
                                                            }).error(function(err){
                                                                   deferred.reject(err);
                                                            })
                                                            return deferred.promise;
                                                     },
                                                        // request resource vendor
                                                        requestResourcevendor : function(requirementId,
                                                                      _id, vendorId,registrationId) {
                                                               var deferred = $q.defer();
                                                               
                                                          debugger;
                                                               $http.post(
                                                                             'rest/requestResource/reqResource/'
                                                            		   + requirementId + '/' + registrationId+ '/'   + _id
                                                                                           + '/' + vendorId 
                                                                                           ).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        praposeResourceCustomer : function(resource_id,
                                                                      resource_registrationid, requirementId,
                                                                      customer_registrationId) {
                                                             
                                                               var deferred = $q.defer();
                                                           
                                                               $http.post(
                                                                             'rest/propsedResource/createProposedResource/'
                                                                                           + requirementId + '/'
                                                                                           + resource_id + '/'
                                                                                           + customer_registrationId
                                                                                           + '/'
                                                                                           + resource_registrationid)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        vendorpraposedresource : function(registrationId) {
                                                        	debugger;
                                                               var deferred = $q.defer();

                                                               $http.get('rest/propsedResource/findResourcesByCustomerId/'
                                                                                           + registrationId + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        // vendor dashboard start
                                                        // Total resources by technology

                                                        totalResourceByTechnology : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/resource/totalResourceByTechnology/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        // Top customers by requirements
                                                        Toptencustomers : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'https://api.myjson.com/bins/avtqg')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // Top Vendors By Resource

                                                        ToptenVendors : function(id) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/resource/topTenVendorsByResources/'
                                                                                           + id).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        // Total resources by technology in cities

                                                        tencities : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/resource/resourcesByCityAndTechnology')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // requirement by technology

                                                       requirementTechnology : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'rest/requirement/requirementsByTopTechnologies')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // requirement by Cities

                                                        requirementCities : function() {
                                                               var deferred = $q.defer();
                                                               $http.get('rest/requirement/requirementsByJobLocation')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        // top Technologies

//                                                        TopTech : function() {
//                                                               var deferred = $q.defer();
//                                                               $http
//                                                                             .get(
//                                                                                           'https://api.myjson.com/bins/sh0hk')
//                                                                             .success(function(response) {
//                                                                                    deferred.resolve(response);
//                                                                             }).error(function(err) {
//                                                                                    deferred.reject(err);
//                                                                             })
//                                                               return deferred.promise;
//                                                        },
                                                        // Req Vs Pro
                                                        ReqVsPro : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'https://api.myjson.com/bins/154imw')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        // req Vs Clo
                                                        ReqVsClo : function() {
                                                               var deferred = $q.defer();
                                                               $http
                                                                             .get(
                                                                                           'https://api.myjson.com/bins/jxtx8')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        // vendor dashboard end

                                                        // customer service
                                                        customeruserlist : function(pageIndex,pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                               var local = localStorage.getItem('registrationId');
                                                               $http.get(
                                                                             'rest/user/findAllByRegistrationId/'
                                                                                           + local + '/'+pageIndex+'/'+pageSizeSelected).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        customeraddrequirment : function(local,pageIndex, pageSizeSelected) {
                                                        	
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/requirement/findRequirementsByRegistrationId/'
                                                                                           + local + '/' + pageIndex
                                                                                           + '/' + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        viewcustomermsa : function(planId) {
                                                          
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/registration/listRegisteredSowusersByRegId/'
                                                                                           + planId).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        // search
                                                        resourcelistaccordion : function() {
                                                        
                                                               var deferred = $q.defer();
                                                               var reg = localStorage
                                                                             .getItem('registrationId');
                                                               $http.get( 'rest/resource/allListsByRegId/'+ reg).success( function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        requirementlistaccordion : function() {
                                                        
                                                               var deferred = $q.defer();
                                                               var reg1 = localStorage
                                                                             .getItem('registrationId');
                                                               $http.get('rest/requirement/allListsByRegId/' + reg1).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        
                                                        
                                                        dashData : function() {
                                                            var deferred = $q.defer();
                                                            $http
                                                                          .get(
                                                                                        'rest/requirement/requirementsByCustomer')
                                                                          .success(function(response) {
                                                                                 deferred.resolve(response);
                                                                          }).error(function(err) {
                                                                                 deferred.reject(err);
                                                                          })
                                                            return deferred.promise;
                                                     },
                                                    /*
                                                     requirementbypostedate : function() {
                                                         var deferred = $q.defer();
                                                         $http
                                                                       .get(
                                                                                     'rest/requirement/requirementsByCustomer')
                                                                       .success(function(response) {
                                                                              deferred.resolve(response);
                                                                       }).error(function(err) {
                                                                              deferred.reject(err);
                                                                       })
                                                         return deferred.promise;
                                                  },
                                                    */    
                                                        

                                                        /*allresLists : function() {
                                                       
                                                               var deferred = $q.defer();
                                                               $http.get('rest/resource/allLists')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },*/
                                                     /*  
                                                     allreqLists : function() {
                                                         
                                                               var deferred = $q.defer();
                                                               $http.get('rest/requirement/allLists')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        */
                                                        // getcompany:function(){
                                                        // debugger;
                                                        // var deferred = $q.defer();
                                                        // $http.get(APIURL+'/ResourceAdda/rest/companies/listCompanies').success(function(response){
                                                        // deferred.resolve(response);
                                                        // }).error(function(err){
                                                        // deferred.reject(err);
                                                        // })
                                                        // return deferred.promise;
                                                        // },
                                                        
                                                        // getbudget:function(){
                                                        // debugger;
                                                        // var deferred = $q.defer();
                                                        // $http.get(APIURL+'/ResourceAdda/rest/budget/litBudget').success(function(response){
                                                        // deferred.resolve(response);
                                                        // }).error(function(err){
                                                        // deferred.reject(err);
                                                        // })
                                                        // return deferred.promise;
                                                        // },
                                                        // getexperience:function(){
                                                        // debugger;
                                                        // var deferred = $q.defer();
                                                        // $http.get(APIURL+'/ResourceAdda/rest/experience/listExperience').success(function(response){
                                                        // deferred.resolve(response);
                                                        // }).error(function(err){
                                                        // deferred.reject(err);
                                                        // })
                                                        // return deferred.promise;
                                                        // },
                                                        // getlocation:function(){
                                                        // debugger;
                                                        // var deferred = $q.defer();
                                                        // $http.get(APIURL+'/ResourceAdda/rest/location/listLocation').success(function(response){
                                                        // deferred.resolve(response);
                                                        // }).error(function(err){
                                                        // deferred.reject(err);
                                                        // })
                                                        // return deferred.promise;
                                                        // },
                                                        // getjobCategory:function(){
                                                        // debugger;
                                                        // var deferred = $q.defer();
                                                        // $http.get(APIURL+'/ResourceAdda/rest/jobCategory/listJobCategory/1/10').success(function(response){
                                                        // deferred.resolve(response);
                                                        // }).error(function(err){
                                                        // deferred.reject(err);
                                                        // })
                                                        // return deferred.promise;
                                                        // },
                                                        menuget : function(role, type) {
                                                               
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/menu/findAllByCondition/'
                                                                                           + role + '/' + type
                                                                                           + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        searchresource : function(Requirement_Id,jobCategory,yearsOfExperience,pageIndex,pageSizeSelected) {
                                                    
                                                               var regid = localStorage.getItem('registrationId');
                                                               var deferred = $q.defer();                                                              
                                                               $http.get(  'rest/resource/findResourcesBySearch/' + Requirement_Id + '/' + jobCategory + '/'+ location+ '/'+ yearsOfExperience +'/'+pageIndex+'/'+pageSizeSelected).success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise; /customerfindResourcesBySearch/
                                                        },
                                                        
                                                        searchResourceGeneral : function(primarySkills,jobCategory,location,experience,pageIndex,pageSizeSelected) {                                                           
                                                        	
                                                            var deferred = $q.defer();                                                              
                                                            $http.get('rest/resource/findResourcesBySearchs/'+"undefined" + '/' + primarySkills + '/' + jobCategory + '/'+ location + '/'+experience +'/'+"undefined" + '/'+"undefined" + '/'+pageIndex+'/'+pageSizeSelected).success(
                                                                          function(response) {
                                                                                 deferred.resolve(response);
                                                                          }).error(function(err) {
                                                                   deferred.reject(err);
                                                            })
                                                            return deferred.promise; 
                                                     },
                                                        
                                                        //vendor search
                                                        searchresourceforvendor: function(skills, jobcategory,location, yearsOfExperience) {
                                                          
                                                            var regid = localStorage.getItem('registrationId');
                                                            var deferred = $q.defer();                                                              
                                                            $http.get(  'rest/resource/findResourcesBySearchs/' + regid + '/' + skills + '/' + jobcategory + '/' + location + '/'+ yearsOfExperience +'/'+ "undefined" +'/'+ "undefined" + '/1/5').success(
                                                                          function(response) {
                                                                                 deferred.resolve(response);
                                                                          }).error(function(err) {
                                                                   deferred.reject(err);
                                                            })
                                                            return deferred.promise;
                                                     },
                                                        searchsidefilterrequirment : function(skills,
                                                                      jobcategory, location, experience,
                                                                      vendors, budget) {
                                                          
                                                               var deferred = $q.defer();
                                                               var regid = localStorage.getItem('registrationId');
                                                               $http.get(
                                                                             'rest/requirement/findRequirementsBySearchs/'
                                                                                           + regid + '/' + skills
                                                                                           + '/' + jobcategory + '/'
                                                                                           + location + '/'
                                                                                           + experience + '/'
                                                                                           + vendors + '/' + budget
                                                                                           + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        searchsidefilterrequirmentvendor : function(
                                                                      skills, jobcategory, location,
                                                                      experience, vendors, budget) { // edited
                                                                                                                              // by
                                                                                                                              // Ikhil
                                                            
                                                               var deferred = $q.defer();
                                                               var customerregid = localStorage.getItem('registrationId');
                                                               $http.get('rest/requirement/findRequirementsByFilter/'
                                                            		   
                                                                                           + skills + '/'
                                                                                           + jobcategory + '/'
                                                                                           + location + '/'
                                                                                           + experience + '/'
                                                                                           + vendors + '/' + budget
                                                                                           + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        searchsidefilterresource : function(skills,
                                                                      jobcategory, location, experience,
                                                                      customers, budget) {
                                                             
                                                               var deferred = $q.defer();
                                                               var regid = localStorage.getItem('registrationId');
                                                              
                                                               $http.get('rest/resource/findResourcesBySearchs/' + regid + '/' + skills + '/' + jobcategory + '/' + location + '/'+ experience + '/' + customers + '/' + budget + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },
                                                        searchsidefilterresourcecutomer : function(
                                                                      skills, jobcategory, location,
                                                                      experience, vendors, budget) {
                                                        	 var regid = localStorage
                                                             .getItem('registrationId');
                                                             
                                                               var deferred = $q.defer();

                                                               $http.get(
                                                                             'rest/resource/findResourcesBySearchs/'
                                                            		   					   + "undefined" + '/'
                                                                                           + skills + '/'
                                                                                           + jobcategory + '/'
                                                                                           + location + '/'
                                                                                           + experience + '/'
                                                                                           + vendors + '/' + budget
                                                                                           + '/1/5').success(
                                                                             function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                      deferred.reject(err);
                                                               })
                                                               return deferred.promise;
                                                        },

                                                        searchrequirement : function(primarySkills,jobCategory,location,totalExperience,pageIndex,pageSizeSelected) {
                                                            
                                                               var deferred = $q.defer();
                                                               var regid = localStorage.getItem('registrationId'); 
                                                               //$http.get('rest/requirement/findRequirementsBySearch/' + regid + '/' + resource_id + '/' + jobCategory + '/' + location+'/' +totalExperience + '/' + pageIndex + '/'
                                                                //       + pageSizeSelected)
                                                               $http.get('rest/requirement/findRequirementsBySearch/'+primarySkills+'/'+ jobCategory + '/' + location+'/' +totalExperience + '/' + pageIndex + '/'
                                                                      + pageSizeSelected)
                                                                            .success(function(response) {
                                                                                    deferred.resolve(response); 
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        searchrequirementCustomerTop : function(primarySkills,jobCategory,jobLocation,totalExperience,pageIndex,pageSizeSelected) {
                                                            
                                                            var deferred = $q.defer();
                                                            var regid = localStorage.getItem('registrationId'); 
                                                            $http.get('rest/requirement/findRequirementsByTopSearchCustomer/' + regid + '/' + primarySkills + '/' + jobCategory + '/' + jobLocation+'/' +totalExperience + '/' + pageIndex + '/'
                                                                    + pageSizeSelected)
                                                                          .success(function(response) {
                                                                                 deferred.resolve(response); 
                                                                          }).error(function(err) {
                                                                                 deferred.reject(err);
                                                                          })
                                                            return deferred.promise;
                                                       },
                                                        

                                                        searchRequirementCustomer : function(primarySkills,jobCategory,location,yearsOfExperience,pageIndex,pageSizeSelected) {
                                                            debugger;
                                                               var deferred = $q.defer();
                                                               var regid = localStorage.getItem('registrationId'); 
                                                               $http.get('rest/resource/findResourcesBySearchs/' + regid + '/'+primarySkills + '/' + jobCategory + '/' + location+'/' +yearsOfExperience +'/'+"undefined" +'/'+"undefined" +'/'+pageIndex + '/'
                                                                       + pageSizeSelected)
                                                                             .success(function(response) {
                                                                            	
                                                                                    deferred.resolve(response); 
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        
                                                        searchrequirementvendor : function(skills, jobcategory, location, experience) {
                                                        
                                                            var deferred = $q.defer();
                                                            var regid = localStorage.getItem('registrationId');
                                                            $http.get('rest/requirement/findRequirementsByFilter/' + skills + '/' + jobcategory + '/'
                                                                    + location + '/'
                                                                    + experience + '/'
                                                                    + "undefined" + '/' + "undefined"
                                                                    + '/1/5')
                                                                          .success(function(response) {
                                                                                 deferred.resolve(response); 
                                                                          }).error(function(err) {
                                                                                 deferred.reject(err);
                                                                          })
                                                            return deferred.promise;
                                                     },
                                                        searchresourcebyid : function(registrationid,requirement_Id, jobcategory,experience,pageIndex,pageSizeSelected) {
                                                               var deferred = $q.defer();

                                                               $http.get('rest/resource/vendorfindResourcesBySearch/' + registrationid + '/' + requirement_Id + '/'+ jobcategory  + '/'+ experience + '/'+pageIndex+'/' +pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        searchrequirementbyid : function(registrationId,skills,jobCategory,location,yearsOfExperience,pageIndex,pageSizeSelected) {
                                                               var deferred = $q.defer();                                                               				
                                                               $http.get('rest/requirement/findRequirementsBySearch/'+ registrationId + '/' + skills + '/'+ jobCategory + '/' +location+"/"+ yearsOfExperience + '/'+pageIndex+'/'+pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },
                                                        search : function(pageIndex, search2,
                                                                      pageSizeSelected) {
                                                               var deferred = $q.defer();
                                                    
                                                               var local = localStorage
                                                                             .getItem("registrationId");
                                                               $http.get(
                                                                             'rest/user/findUserByCondition/'
                                                                                           + local + '/' + search2
                                                                                           + '/' + pageIndex + '/'
                                                                                           + pageSizeSelected)
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        },

                                                        customerrequestedresource : function(
                                                                      registrationId) {
                                                               var deferred = $q.defer();
                                                               $http.get(
                                                                             'rest/requestResource/findResourcesByVendorId/'
                                                                                           + registrationId + '/1/5')
                                                                             .success(function(response) {
                                                                                    deferred.resolve(response);
                                                                             }).error(function(err) {
                                                                                    deferred.reject(err);
                                                                             })
                                                               return deferred.promise;
                                                        }
                                                 }

                                          } ])
