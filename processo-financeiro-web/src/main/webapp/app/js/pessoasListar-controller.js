angular.module('ProcessoFinanceiro')
    .controller('PessoasCtrlListar', ['$scope', '$http', '$window', '$location', 'growl', 'moment',
        function ($scope, $http, $window, $location, growl, moment) {


            $scope.excluirPessoa = excluirPessoa;








            $scope.removeRow = removeRow;




            $scope.propertyName = 'nome';
            $scope.reverse = false;


            $scope.sortBy = function (propertyName) {
                $scope.reverse = ($scope.propertyName === propertyName) ? !$scope.reverse : false;
                $scope.propertyName = propertyName;
            };





            $scope.editar = function (url, pessoa, refresh) {

                if (pessoa != undefined) {



                    $window.sessionStorage.setItem('pessoa', JSON.stringify(angular.copy(pessoa)));


                } else {
                    $window.sessionStorage.setItem('pessoa', 'false');
                }
                if (refresh || $scope.$$phase) {
                    $window.location.href = url;
                } else {
                    $location.path(url);
                    $scope.$apply();
                }
            }




            function buscarPessoas() {
                $http.get("http://localhost:8888/processofinanceiro/services/pessoas")
                    .then(function (response) {
                        $scope.pessoas = response.data;
                        var pessoas = eval($scope.pessoas);
                        for (var i = 0; i < pessoas.length; i++) {
                            if (pessoas[i].numero == 0) {
                                pessoas[i].numero = '';
                            }
                        }

                        console.log($scope.pessoas);
                        console.log('Dados retornados')
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


            $scope.filtrar = function (filtros) {
                $http.get("http://localhost:8888/processofinanceiro/services/pessoas/filtros/" + filtros)
                    .then(function (response) {
                        $scope.pessoas = response.data;
                        var pessoas = eval($scope.pessoas);
                        for (var i = 0; i < pessoas.length; i++) {
                            if (pessoas[i].numero == 0) {
                                pessoas[i].numero = '';
                            }
                        }

                        console.log($scope.pessoas);
                        console.log('Dados retornados')
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

            function removeRow(cpfCnpj) {
                var index = -1;
                var comArr = eval($scope.pessoas);
                for (var i = 0; i < comArr.length; i++) {
                    if (comArr[i].cpfCnpj === cpfCnpj) {
                        index = i;
                        break;
                    }
                }
                if (index === -1) {
                    alert("Algo está errado");
                }
                $scope.pessoas.splice(index, 1);
                buscarPessoas();
            }

            function excluirPessoa(pessoa) {
                if (confirm("Deseja realmente excluir?")) {


                    console.log(pessoa);
                    $http.delete("http://localhost:8888/processofinanceiro/services/pessoas/" + pessoa.cpfCnpj)
                        .then(function (response) {




                            growl.success(response.data.mensagem);
                            removeRow(pessoa.cpfCnpj);




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






            $scope.init = function () {

                $('.data').mask('00-00-0000');

                $window.sessionStorage.setItem('insercao', JSON.stringify(false));
                buscarPessoas();
            };


        }
    ]);