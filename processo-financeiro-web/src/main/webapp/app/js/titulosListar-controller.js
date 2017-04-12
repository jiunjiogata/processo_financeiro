angular.module('ProcessoFinanceiro')
    .controller('TitulosCtrlListar', ['$scope', '$http', '$window', '$location', 'growl', 'moment',
        function ($scope, $http, $window, $location, growl, moment) {


            $scope.excluirTitulo = excluirTitulo;

            var modoOrdem = 'ASC';



            $scope.propertyName = 'nome';
            $scope.reverse = false;




            $scope.removeRow = removeRow;

            $scope.ordenar = function (ordenador, tipo) {

                if (modoOrdem == 'ASC') {
                    modoOrdem = 'DESC';
                } else {
                    modoOrdem = 'ASC';
                }

                $http.post("http://localhost:8888/processofinanceiro/services/titulos/ordenar/" + tipo + "/" + ordenador + "/" + modoOrdem, $scope.titulos)
                    .then(function (response) {
                        $scope.titulos = response.data;




                    }).catch(function onError(response) {

                        if (response.status == 500 || response.data.mensagem == null) {

                            growl.error("Erro no servidor");
                            console.log("Erro no servidor");
                        } else {
                            console.log(response.data.mensagem);
                            growl.error(response.data.mensagem);
                        }


                    });

            }

            $scope.sortBy = function (propertyName) {
                $scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
                $scope.propertyName = propertyName;
            };

            $scope.filtrar = function (filtro, tipo) {
                $http.get("http://localhost:8888/processofinanceiro/services/titulos/filtrar/" + filtro + "/" + tipo)
                    .then(function (response) {
                        $scope.titulos = response.data;




                    }).catch(function onError(response) {

                        if (response.status == 500 || response.data.mensagem == null) {

                            growl.error("Erro no servidor");
                            console.log("Erro no servidor");
                        } else {
                            console.log(response.data.mensagem);
                            growl.error(response.data.mensagem);
                        }


                    });
            }


            $scope.editar = function (url, titulo, tipo, refresh) {

                if (titulo != undefined) {



                    $window.sessionStorage.setItem('titulo', JSON.stringify(angular.copy(titulo)));


                } else {
                    $window.sessionStorage.setItem('tipo', tipo);
                    $window.sessionStorage.setItem('titulo', 'false');
                }
                if (refresh || $scope.$$phase) {
                    $window.location.href = url;
                } else {
                    $location.path(url);
                    $scope.$apply();
                }
            }



            $scope.filtragem = function (filtros) {
                $http.post("http://localhost:8888/processofinanceiro/services/titulos/filtros/", filtros)
                    .then(function (response) {
                        $scope.titulos = response.data;




                    }).catch(function onError(response) {

                        if (response.status == 500 || response.data.mensagem == null) {

                            growl.error("Erro no servidor");
                            console.log("Erro no servidor");
                        } else {
                            console.log(response.data.mensagem);
                            growl.error(response.data.mensagem);
                        }


                    });
            }











            function buscarTitulos(tipo) {
                $http.get("http://localhost:8888/processofinanceiro/services/titulos/tipo/" + tipo)
                    .then(function (response) {
                        $scope.titulos = response.data;


                    }).catch(function onError(response) {

                        if (response.status == 500 || response.data.mensagem == null) {

                            growl.error("Erro no servidor");
                            console.log("Erro no servidor");
                        } else {
                            console.log(response.data.mensagem);
                            growl.error(response.data.mensagem);
                        }


                    });
            }




            function removeRow(numero) {
                var index = -1;
                var comArr = eval($scope.titulos);
                for (var i = 0; i < comArr.length; i++) {
                    if (comArr[i].numero === numero) {
                        index = i;
                        break;
                    }
                }
                if (index === -1) {
                    alert("Algo está errado");
                }
                $scope.titulos.splice(index, 1);
            }

            function excluirTitulo(titulo) {
                if (confirm("Deseja realmente excluir?")) {


                    console.log(titulo);
                    $http.post("http://localhost:8888/processofinanceiro/services/titulos/excluir/", titulo)
                        .then(function (response) {




                            growl.success(response.data.mensagem);
                            removeRow(titulo.numero);




                        }).catch(function onError(response) {


                            if (response.status == 500 || response.data.mensagem == null) {

                                growl.error("Erro no servidor");
                                console.log("Erro no servidor");
                            } else {
                                console.log(response.data.mensagem);
                                growl.error(response.data.mensagem);
                            }


                        });
                }


            }






            $scope.init = function (tipo) {
                 $('.data').mask('00-00-0000');
                $scope.filtros = {
                    tipo: tipo
                }
                $window.sessionStorage.setItem('insercao', JSON.stringify(false));
                buscarTitulos($scope.filtros.tipo);
            };


        }
    ]);