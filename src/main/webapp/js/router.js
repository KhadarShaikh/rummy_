resourceApp
		.config([
				'$stateProvider',
				'$urlRouterProvider',
				'$locationProvider',
				'$httpProvider',
				function($stateProvider, $urlRouterProvider, $locationProvider,
						$httpProvider) {
					// var roles=localStorage.getItem('registrationType');
					var accessLevels = {
						admin : 'admin',
						RA : 'RA',
						vendor : 'vendor',
						customer : 'customer'
					};
					$urlRouterProvider.otherwise('/');

					$stateProvider
							/*
							 * .state('homepage', { url : '/', templateUrl :
							 * 'partials/homepage.html', controller : 'homeCtrl' })
							 */
							.state('aboutVendors', {
								url : '/aboutVendors',
								templateUrl : 'partials/aboutvendors.html',

							})
							.state('aboutCustomers', {
								url : '/aboutCustomers',
								templateUrl : 'partials/aboutCustomers.html',

							})

							.state('aboutRA', {
								url : '/aboutRA',
								templateUrl : 'partials/aboutRA.html',

							})
							.state('Service', {
								url : '/Service',
								templateUrl : 'partials/services.html'

							})
							.state('login', {
								url : '/',
								templateUrl : 'partials/login.html',
								controller : 'LoginController'
							})
							.state('forgotpassword', {
								url : '/forgotpassword',
								templateUrl : 'partials/forgotpassword.html',
								controller : 'LoginController'
							})

							.state('Plan', {
								url : '/Plans',
								templateUrl : 'partials/choosePlan.html',
								controller : 'LoginController'
							})

							.state('Register', {
								url : '/Register',
								templateUrl : 'partials/Register.html',
								controller : 'LoginController'

							})

							// RA state starts here

							.state('RA', {
								url : '/RA',
								abstract : true,
								templateUrl : 'partials/RA/RA.html',
								cache : false,
								controller : 'RACtrl'
							})
							.state('RA.dashboard', {
								url : '/RADashboard',
								templateUrl : 'partials/RA/RAdashboard.html',
								cache : false,
								controller : 'RAdashboardCtrl'
							})
							.state('RA.aboutRA1', {
								url : '/aboutRA1',
								templateUrl : 'partials/RA/aboutRA1.html',
								cache : false,
							// controller : 'RAdashboardCtrl'
							})
							.state(
									'RA.companyProfile',
									{
										url : '/companyProfile',
										templateUrl : 'partials/RA/companyProfile.html',
										cache : false
									})
							.state('RA.contactSale', {
								url : '/contactSale',
								templateUrl : 'partials/RA/contactSale.html',
								cache : false
							})
							.state(
									'RA.socialRecruiting',
									{
										url : '/socialRecruiting',
										templateUrl : 'partials/RA/socialRecruiting.html',
										cache : false
									})
							.state('RA.report', {
								url : '/report',
								templateUrl : 'partials/RA/report.html',
								cache : false
							})
							.state('RA.openWeb', {
								url : '/openWeb',
								templateUrl : 'partials/RA/openWeb.html',
								cache : false
							})
							.state('RA.browseJobs', {
								url : '/browseJobs',
								templateUrl : 'partials/RA/browseJobs.html',
								cache : false
							})
							.state(
									'RA.browseCompanies',
									{
										url : '/browseCompanies',
										templateUrl : 'partials/RA/browseCompanies.html',
										cache : false
									})
							.state('RA.skillsCenter', {
								url : '/skillsCenter',
								templateUrl : 'partials/RA/skillsCenter.html',
								cache : false
							})
							.state(
									'RA.siteBrowseCompanies',
									{
										url : '/siteBrowseCompanies',
										templateUrl : 'partials/RA/siteBrowseCompanies.html',
										cache : false
									})
							.state(
									'RA.siteSkillsCenter',
									{
										url : '/siteSkillsCenter',
										templateUrl : 'partials/RA/siteSkillsCenter.html',
										cache : false
									})
							.state('RA.uploadResume', {
								url : '/uploadResume',
								templateUrl : 'partials/RA/uploadResume.html',
								cache : false,
								controller : 'uploadresumeCtrl'
							})
							.state('RA.subscription', {
								url : '/subscription',
								templateUrl : 'partials/RA/subscription.html',
								cache : false,
								controller : 'RACtrl'
							})
							.state('RA.payment', {
								url : '/payment',
								templateUrl : 'partials/RA/payment.html',
								cache : false,
								controller : 'paymentCtrl'
							})
							.state('RA.ReviewProduct', {
								url : '/ReviewProduct/:planid',
								templateUrl : 'partials/RA/ReviewProduct.html',
								cache : false,
								controller : 'buynewCtrl'

							})
							.state('RA.RAlist', {
								url : '/RAlist',
								templateUrl : 'partials/RA/RAlist.html',
								cache : false,
								controller : 'RAlistCtrl'

							})
							.state('RA.addRA', {
								url : '/addRA',
								templateUrl : 'partials/RA/addRA.html',
								cache : false,
								controller : 'addRACtrl'
							})
							.state('RA.updateRA', {
								url : '/updateRA/:userId',
								templateUrl : 'partials/RA/updateRA.html',
								cache : false,
								controller : 'updateRACtrl'
							})
							.state('RA.buynow', {
								url : '/RAbuynow',
								templateUrl : 'partials/RA/buynow.html',
								cache : false,
								controller : 'buynowCtrl'
							})
							.state(
									'RA.plan',
									{
										url : '/PlanManagement',
										templateUrl : 'partials/RA/planmanagement.html',
										cache : false,
										controller : 'planCtrl'
									})
							.state('RA.Planadd', {
								url : '/addPlan',
								templateUrl : 'partials/RA/addplan.html',
								cache : false,
								controller : 'addplanCtrl'
							})
							.state('RA.updateplan', {
								url : '/updateplan/:planId',
								templateUrl : 'partials/RA/updateplan.html',
								cache : false,
								controller : 'updateplanCtrl'
							})
							.state('RA.postarequirementlist', {
								url : '/postarequirementlist',
								templateUrl : 'partials/RA/postareqlist.html',
								cache : false,
								controller : 'postareqCtrl'
							})
							.state(
									'RA.addpostrequirement',
									{
										url : '/addpostarequirement',
										templateUrl : 'partials/RA/addrequirement.html',
										cache : false,
										controller : 'addpostreqCtrl'
									})
							.state('RA.updatepostarequirement', {
								url : '/updatepostarequirement/:postId',
								templateUrl : 'partials/RA/updatepostreq.html',
								cache : false,
								controller : 'updatepostareqCtrl'
							})
							.state(
									'RA.registerContact',
									{
										url : '/registrationContactlist',
										templateUrl : 'partials/RA/registercontactlist.html',
										cache : false,
										controller : 'registercontactCtrl'
									})
							.state(
									'RA.registeraddContact',
									{
										url : '/registeraddcontact',
										templateUrl : 'partials/RA/registeraddcontact.html',
										cache : false,
										controller : 'registercontactCtrl'
									})
							.state(
									'RA.updateregistercontact',
									{
										url : '/updateregistercontact/:contactId',
										templateUrl : 'partials/RA/updateregistercontact.html',
										cache : false,
										controller : 'updateregistercontactCtrl'
									})
							.state('RA.alluserlist', {
								url : '/AlluserList',
								templateUrl : 'partials/RA/alluserlist.html',
								cache : false,
								controller : 'alluserlistCtrl'
							})
							.state(
									'RA.Jobtype',
									{
										url : '/Jobtype',
										templateUrl : 'partials/RA/Master/Jobtype.html',
										cache : false,
										controller : 'JobtypeController'
									})
							.state(
									'RA.JobtypeAdd',
									{
										url : '/Jobtype',
										templateUrl : 'partials/RA/Master/JobtypeAdd.html',
										cache : false,
										controller : 'jobtypeaddCtrl'
									})
							.state(
									'RA.JobtypeEdit',
									{
										url : '/Jobtype/:alluser',
										templateUrl : 'partials/RA/Master/JobtypeEdit.html',
										cache : false,
										controller : 'jobtypeupdateCtrl'
									})
							.state(
									'RA.PrimarySkills',
									{
										url : '/Primary',
										templateUrl : 'partials/RA/Master/PrimarySkillsAll.html',
										cache : false,
										controller : 'primaryskillsController'
									})
							.state(
									'RA.PrimaryskillsAdd',
									{
										url : '/Primary',
										templateUrl : 'partials/RA/Master/PrimaryskillsAdd.html',
										cache : false,
										controller : 'primaryskillsAddCtrl'
									})

							.state(
									'RA.PrimaryskillsUpdate',
									{
										url : '/Primary/:alluser',
										templateUrl : 'partials/RA/Master/PrimaryskillsUpdate.html',
										cache : false,
										controller : 'primaryskillsupdateCtrl'
									})

							.state('RA.alluserAdd', {
								url : '/alluserAdd',
								templateUrl : 'partials/RA/alluseradd.html',
								cache : false,
								controller : 'alluseraddCtrl'
							})
							.state('RA.alluserupdate', {
								url : '/alluserupdate/:alluserId',
								templateUrl : 'partials/RA/alluserupdate.html',
								cache : false,
								controller : 'alluserupdateCtrl'
							})
							.state('RA.resourcelist', {
								url : '/resourcelist',
								templateUrl : 'partials/RA/resourcelist.html',
								cache : false,
								controller : 'resourcelistCtrl'
							})

							.state(
									'RA.resourcesearchList',
									{
										url : '/resourcesearchList',
										templateUrl : 'partials/RA/resourcesearch.html',
										cache : false,
										controller : 'resourcesearchCtrl'
									})

							/*
							 * .state('RA.bulkUpload',{ url:'/addresource',
							 * templateUrl: 'partials/RA/bulkUpload.html',
							 * cache: false, controller: 'addresourceCtrl' })
							 */
							.state('RA.addresource', {
								url : '/addresource',
								templateUrl : 'partials/RA/addresource.html',
								cache : false,
								controller : 'addresourceCtrl'
							})
							.state(
									'RA.updateresource',
									{
										url : '/updateresource/:resourceId',
										templateUrl : 'partials/RA/updateresource.html',
										cache : false,
										controller : 'updateresourceCtrl'
									})

							.state(
									'RA.JoinWithIn',
									{
										url : '/JoinWithIn',
										templateUrl : 'partials/RA/Master/JoinWithIn.html',
										cache : false,
										controller : 'JoinWithInCtrl'
									})
							.state(
									'RA.addnewJoin',
									{
										url : '/addnewJoin',
										templateUrl : 'partials/RA/Master/addnewJoin.html',
										cache : false,
										controller : 'JoinWithInCtrl'
									})
							.state(
									'RA.updatejoinWithIn',
									{
										url : '/JoinWithIn/:joinWithIn',
										templateUrl : 'partials/RA/Master/updatejoinWithIn.html',
										cache : false,
										controller : 'JoinWithInUpdateCtrl'
									})

							.state(
									'RA.jobcategory',
									{
										url : '/jobcategory',
										templateUrl : 'partials/RA/Master/jobcategory.html',
										cache : false,
										controller : 'jobgetCtrl'
									})
							.state(
									'RA.jobcategoryadd',
									{
										url : '/jobcategoryadd',
										templateUrl : 'partials/RA/Master/jobcategoryadd.html',
										cache : false,
										controller : 'postCtrl'
									})
							.state(
									'RA.jobcategoryupdate',
									{
										url : '/jobcategoryupdate/:putcategory',
										templateUrl : 'partials/RA/Master/updatejobcategory.html',
										cache : false,
										controller : 'putCtrl'
									})
							.state(
									'RA.secondaryskill',
									{
										url : '/secondaryskill',
										templateUrl : 'partials/RA/Master/secondaryskill.html',
										cache : false,
										controller : 'secondaryskillCtrl'
									})
							.state(
									'RA.secondaryskilladd',
									{
										url : '/secondaryskilladd',
										templateUrl : 'partials/RA/Master/secondaryskilladd.html',
										cache : false,
										controller : 'secondaryaddCtrl'
									})
							.state(
									'RA.secondaryskilledit',
									{
										url : '/secondaryskilledit/:putskill',
										templateUrl : 'partials/RA/Master/secondaryskilledit.html',
										cache : false,
										controller : 'secondaryeditCtrl'
									})

							.state('RA.postresource', {
								url : '/postresource',
								templateUrl : 'partials/RA/postresource.html',
								cache : false,
								controller : 'postresourceCtrl'
							})
							.state(
									'RA.postresourceById',
									{
										url : '/postresourceById/:localId/:resourceById/:objectid',
										templateUrl : 'partials/RA/postresourceById.html',
										cache : false,
										controller : 'postresourceByIdCtrl'
									})
							.state(
									'RA.resourceMap',
									{
										url : '/resourceMap',
										templateUrl : 'partials/RA/resourcemaplist.html',
										cache : false,
										controller : 'resourcemapCtrl'
									})

							// summary

							.state('RA.requirementsummary', {
								url : '/requirementsummary/:postId',
								templateUrl : 'partials/RA/rareqsummary.html',
								cache : false,
								controller : 'customerupdatepostreqCtrl'
							})

							.state(
									'RA.resourcesummary',
									{
										url : '/resourcesummary/:resourceId',
										templateUrl : 'partials/RA/raresourcesummary.html',
										cache : false,
										controller : 'vendorupdateresourceCtrl'
									})
							// RA state end here

							// customer state starts here
							.state(
									'customer',
									{
										url : '/customer',
										abstract : true,
										templateUrl : 'partials/customer/customer.html',
										cache : false,
										controller : 'customerCtrl'
									})
							.state(
									'customer.subscription',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/subscription',
										templateUrl : 'partials/customer/subscription.html',
										cache : false,
										controller : 'customerCtrl'
									})
							.state(
									'customer.buynew',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customerbuynew',
										templateUrl : 'partials/customer/buynew.html',
										cache : false,
										controller : 'customerbuynowCtrl'
									})
							.state(
									'customer.ReviewProduct',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/ReviewProduct/:planid',
										templateUrl : 'partials/customer/ReviewProduct.html',
										cache : false,
										controller : 'customerbuynewCtrl'

									})

							.state(
									'customer.payment',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/payment',
										templateUrl : 'partials/customer/payment.html',
										cache : false,
										controller : 'customerbuynewCtrl'
									})
							// .state('customer.dashboard',{
							// url: '/customerdashboard',
							// templateUrl:
							// 'partials/customer/customerdashboard.html',
							// cache: false,
							// controller: 'customerdashboardCtrl'
							// })
							.state(
									'customer.dashboard',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : "/customerdashboard",
										templateUrl : "partials/customer/customerdashboard.html",
										controller : "customerdashboardCtrl",

									})
							.state(
									'customer.alluserlist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customeralluserlist',
										templateUrl : 'partials/customer/customeralluserlist.html',
										cache : false,
										controller : 'customeruserlistCtrl'
									})
							.state(
									'customer.alluserAdd',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customeralluserAdd',
										templateUrl : 'partials/customer/customeralluseradd.html',
										cache : false,
										controller : 'customeruseraddCtrl'
									})
							.state(
									'customer.alluserupdate',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customeralluserupdate/:alluserId',
										templateUrl : 'partials/customer/customeralluserupdate.html',
										cache : false,
										controller : 'customer1updateCtrl'
									})
							.state(
									'customer.postrequirement',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customerrequirementList',
										templateUrl : 'partials/customer/customerpostreq.html',
										cache : false,
										controller : 'customerpostareqCtrl'
									})
							.state(
									'customer.addpostrequirement',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/addpostarequirement',
										templateUrl : 'partials/customer/customeraddreq.html',
										cache : false,
										controller : 'customeraddpostreqCtrl'
									})
							.state(
									'customer.updatepostarequirement',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/updatepostarequirement/:postId',
										templateUrl : 'partials/customer/customerupdatepostreq.html',
										cache : false,
										controller : 'customerupdatepostreqCtrl'
									})
							.state(
									'customer.requirementsummary',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/requirementsummary/:postId',
										templateUrl : 'partials/customer/customerreqsummary.html',
										cache : false,
										controller : 'customerupdatepostreqCtrl'
									})
							.state(
									'customer.resourcesummary',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/resourcesummary/:resourceId',
										templateUrl : 'partials/customer/customerresourcesummary.html',
										cache : false,
										controller : 'customergetresourceCtrl'
									})
							.state(
									'customer.ressummary',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/resourcesummary/:resourceId',
										templateUrl : 'partials/customer/customerressummary.html',
										cache : false,
										controller : 'customergetresourceCtrl'
									})
							.state(
									'customer.resoursummary',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/resourcesummary/:resourceId',
										templateUrl : 'partials/customer/customerresoursummary.html',
										cache : false,
										controller : 'customergetresourceCtrl'
									})
							.state(
									'customer.resourceMap',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customerresourceMap',
										templateUrl : 'partials/customer/customerresourcemaplist.html',
										cache : false,
										controller : 'customerresourcemapCtrl'
									})
							.state(
									'customer.customerlist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customerlist',
										templateUrl : 'partials/customer/customerList.html',
										cache : false,
										controller : 'customerlistCtrl'

									})
							.state(
									'customer.updatepro',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/updatecustomer/:userId',
										templateUrl : 'partials/customer/customerupdateprofile.html',
										cache : false,
										controller : 'customerupdateproCtrl'
									})
							.state(
									'customer.resourcelist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customerresourcelist',
										templateUrl : 'partials/customer/customerallresource.html',
										cache : false,
										controller : 'customerresourcelistCtrl'
									})
							// .state(
							// 'customer.resourcesearch',
							// {
							// resolve : {
							// 'check' : function($q, $location,
							// $rootScope) {
							// var defered = $q.defer();
							// debugger;
							// if ($rootScope.roles !== 'customer') {
							// defered.reject();
							// delete $rootScope.roles;
							// $location.path('/');
							//
							// } else {
							// defered.resolve(true);
							// }
							// return defered.promise;
							//
							// }
							// },
							// url : '/customerresourcesearch',
							// templateUrl :
							// 'partials/customer/customerresourcesearch.html',
							// cache : false,
							// controller : 'customerrequirementsearachCtrl'
							// })
							.state(
									'customer.resourcesearch',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customerresourcesearch',
										templateUrl : 'partials/customer/customerresourcesearch.html',
										cache : false,
										controller : 'customerrequirementsearachCtrl'
									})
							.state(
									'customer.resourcesummarydetails',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/customereqsummary/:postId',
										templateUrl : 'partials/customer/customerresourcesummarydetails.html',
										cache : false,
										controller : 'customerupdatepostreqCtrl'
									})
							.state(
									'customer.requirementsearach',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/requirementsearch',
										templateUrl : 'partials/customer/customersearchrequirement.html',
										cache : false,
										controller : 'customerrequirementsearachCtrl'
									})
							.state(
									'customer.vendorpraposedresource',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'customer') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;

											}
										},
										url : '/vendorproposedresource',
										templateUrl : 'partials/customer/vendorproposedresource.html',
										cache : false,
										controller : 'vendorpraposedresourceCtrl'

									})

							// customer state end here

							// vendor state starts here
							.state('vendor', {
								url : '/vendor',
								abstract : true,
								templateUrl : 'partials/vendor/vendor.html',
								cache : false,
								controller : 'vendorCtrl'

							})

							.state('vendor.aboutRA1', {
								url : '/aboutRA1',
								templateUrl : 'partials/RA/aboutRA1.html',
								cache : false,
							// controller : 'RAdashboardCtrl'
							})
							.state(
									'vendor.companyProfile',
									{
										url : '/companyProfile',
										templateUrl : 'partials/RA/companyProfile.html',
										cache : false
									})
							.state('vendor.contactSale', {
								url : '/contactSale',
								templateUrl : 'partials/RA/contactSale.html',
								cache : false
							})
							.state(
									'vendor.socialRecruiting',
									{
										url : '/socialRecruiting',
										templateUrl : 'partials/RA/socialRecruiting.html',
										cache : false
									})
							.state('vendor.report', {
								url : '/report',
								templateUrl : 'partials/RA/report.html',
								cache : false
							})
							.state('vendor.openWeb', {
								url : '/openWeb',
								templateUrl : 'partials/RA/openWeb.html',
								cache : false
							})
							.state('vendor.browseJobs', {
								url : '/browseJobs',
								templateUrl : 'partials/RA/browseJobs.html',
								cache : false
							})
							.state(
									'vendor.browseCompanies',
									{
										url : '/browseCompanies',
										templateUrl : 'partials/RA/browseCompanies.html',
										cache : false
									})
							.state('vendor.skillsCenter', {
								url : '/skillsCenter',
								templateUrl : 'partials/RA/skillsCenter.html',
								cache : false
							})
							.state(
									'vendor.siteBrowseCompanies',
									{
										url : '/siteBrowseCompanies',
										templateUrl : 'partials/RA/siteBrowseCompanies.html',
										cache : false
									})
							.state(
									'vendor.siteSkillsCenter',
									{
										url : '/siteSkillsCenter',
										templateUrl : 'partials/RA/siteSkillsCenter.html',
										cache : false
									})

							.state(
									'vendor.subscription',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/subscription',
										templateUrl : 'partials/vendor/subscription.html',
										cache : false,
										controller : 'vendorCtrl'
									})
							.state(
									'vendor.buynew',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorbuynew',
										templateUrl : 'partials/vendor/buynew.html',
										cache : false,
										controller : 'vendorbuynowCtrl'
									})
							.state(
									'vendor.ReviewProduct',
									{
										url : '/ReviewProduct/:planid',
										templateUrl : 'partials/vendor/ReviewProduct.html',
										cache : false,
										controller : 'vendorbuynewCtrl'

									})
							.state(
									'vendor.payment',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/payment',
										templateUrl : 'partials/vendor/payment.html',
										cache : false,
										controller : 'paymentCtrl'
									})
							// .state('vendor.dashboard',{
							// url: '/vendordashboard',
							// templateUrl:
							// 'partials/vendor/vendorDashboard.html',
							// cache: false,
							// controller: 'vendordashboardCtrl'
							//                	
							// })
							.state(
									'vendor.dashboard',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : "/vendordashboard",
										templateUrl : "partials/vendor/vendorDashboard.html",
										controller : "vendordashboardCtrl",

									})
							.state(
									'vendor.alluserlist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendoralluserlist',
										templateUrl : 'partials/vendor/vendoralluser.html',
										cache : false,
										controller : 'vendoruserlistCtrl'
									})
							.state(
									'vendor.alluserAdd',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendoralluserAdd',
										templateUrl : 'partials/vendor/vendoralluseradd.html',
										cache : false,
										controller : 'vendoruseraddCtrl'
									})
							.state(
									'vendor.alluserupdate',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendoralluserupdate/:alluserId',
										templateUrl : 'partials/vendor/vendoralluserupdate.html',
										cache : false,
										controller : 'vendor1updateCtrl'
									})
							.state(
									'vendor.postresource',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorpostresource',
										templateUrl : 'partials/vendor/vendorpostresource.html',
										cache : false,
										controller : 'vendorpostresourceCtrl'
									})
							.state(
									'vendor.postresourceById',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorpostresourceById/:localId/:resourceById/:objectid',
										templateUrl : 'partials/vendor/vendorpostresourceById.html',
										cache : false,
										controller : 'vendorpostresourceByIdCtrl'
									})

							.state(
									'vendor.resourcelist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/',
										templateUrl : 'partials/vendor/vendorresourcelist.html',
										cache : false,
										controller : 'vendorresourcelistCtrl'
									})
							.state(
									'vendor.addresource',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendoraddresource',
										templateUrl : 'partials/vendor/vendoraddresource.html',
										cache : false,
										controller : 'vendoraddresourceCtrl'
									})

							.state(
									'vendor.updateresource',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorupdateresource/:resourceId',
										templateUrl : 'partials/vendor/vendorupdateresource.html',
										cache : false,
										controller : 'vendorupdateresourceCtrl'
									})
							.state(
									'vendor.resourcesummary',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/resourcesummary/:resourceId',
										templateUrl : 'partials/vendor/vendorresourcesummary.html',
										cache : false,
										controller : 'vendorupdateresourceCtrl'
									})
							.state(
									'vendor.requirementsummary',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/requirementsummary/:postId',
										templateUrl : 'partials/vendor/vendorreqsummary.html',
										cache : false,
										controller : 'vendorreqsummaryCtrl'
									})
							.state(
									'vendor.resourceMap',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorresourceMap',
										templateUrl : 'partials/vendor/vendorresourcemaplist.html',
										cache : false,
										controller : 'vendorresourcemapCtrl'
									})
							.state(
									'vendor.vendorlist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorlist',
										templateUrl : 'partials/vendor/vendorList.html',
										cache : false,
										controller : 'vendorlistCtrl'

									})
							.state(
									'vendor.updatepro',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/updatevendor/:userId',
										templateUrl : 'partials/vendor/vendorupdateprofile.html',
										cache : false,
										controller : 'vendorupdateproCtrl'
									})
							.state(
									'vendor.requirementlist',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
												
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/vendorrequirementlist',
										templateUrl : 'partials/vendor/vendorallreq.html',
										cache : false,
										controller : 'vendorreqlistCtrl'
									})

							.state(
									'vendor.resourcecategory',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/resourcesearch1',
										templateUrl : 'partials/vendor/resourcesearch.html',
										cache : false,
										controller : 'resourcesearch1Ctrl'
									})
							.state(
									'vendor.requirementsearach',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/requirementsearch',
										templateUrl : 'partials/vendor/vendorsearchrequirement.html',
										cache : false,
										controller : 'requirementsearchCtrl'
									})
							.state(
									'vendor.customerrequested',
									{
										resolve : {
											'check' : function($q, $location,
													$rootScope) {
												var defered = $q.defer();
											
												if ($rootScope.roles !== 'vendor') {
													defered.reject();
													delete $rootScope.roles;
													$location.path('/');

												} else {
													defered.resolve(true);
												}
												return defered.promise;
											}
										},
										url : '/customerrequestedresource',
										templateUrl : 'partials/vendor/customerrequested.html',
										cache : false,
										controller : 'customerrequestedresourceCtrl'

									})

					//

					/*
					 * Register error provider that shows message on failed
					 * requests or redirects to login page on unauthenticated
					 * requests
					 */
					$httpProvider.interceptors.push(function($q, $rootScope,
							$location) {
						return {
							'responseError' : function(rejection) {
								var status = rejection.status;
								var config = rejection.config;
								var method = config.method;
								var url = config.url;

								if (status == 401) {
									$location.path("/login");
								} else {
									$rootScope.error = method + " on " + url
											+ " failed with status " + status;
								}

								return $q.reject(rejection);
							}
						};
					});

					/*
					 * Registers auth token interceptor, auth token is either
					 * passed by header or by query parameter as soon as there
					 * is an authenticated user
					 */
					$httpProvider.interceptors
							.push(function($q, $rootScope, $location) {
								return {
									'request' : function(config) {
										var isRestCall = config.url
												.indexOf('rest') == 0;
										if (isRestCall
												&& angular
														.isDefined($rootScope.accessToken)) {
											var accessToken = $rootScope.accessToken;
											if (exampleAppConfig.useAccessTokenHeader) {
												config.headers['X-Access-Token'] = accessToken;
											} else {
												config.url = config.url
														+ "?token="
														+ accessToken;
											}
										}
										return config || $q.when(config);
									}
								};

							}

							);
					//          
				} ])
