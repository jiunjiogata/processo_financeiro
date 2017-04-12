angular.module('ProcessoFinanceiro')
    .controller('TitulosCtrlEditar', ['$scope', '$http', '$window', '$location', 'growl', 'moment',
        function ($scope, $http, $window, $location, growl, moment) {

            $scope.cadastrar = cadastrar;
            $scope.alterar = alterar;
            $scope.jurosCalculado = jurosCalculado;
            $scope.calculoPorcentagemJuros = calculoPorcentagemJuros;
            $scope.descontoCalculado = descontoCalculado;
            $scope.calculoPorcentagemDesconto = calculoPorcentagemDesconto;

            
            $scope.modo;

            $scope.currentItem;

            $scope.titulo = {
                tipo: null,
                dataCriacao: null
            }


           



            function jurosCalculado() {
                $scope.titulo.valorJurosCalculado = $scope.titulo.valorTitulo / 100 * $scope.porcentagemJuros;

                if ($scope.titulo.dataPagamento > $scope.titulo.dataVencimento) {
                    var a = moment($scope.titulo.dataPagamento, 'DD-MM-YYYY');
                    var b = moment($scope.titulo.dataVencimento, 'DD-MM-YYYY');
                    console.log(a.diff(b, 'days'));
                    var diasAtraso = a.diff(b, 'days');
                    $scope.titulo.valorJurosSugerido = ((($scope.porcentagemJurosSugerido / 100) * $scope.titulo.valorTitulo) / 30) * diasAtraso;
                    $scope.titulo.valorPago = $scope.titulo.valorTitulo + $scope.titulo.valorJuros + $scope.titulo.valorJurosCalculado - $scope.titulo.valorDesconto;

                } else {
                    $scope.titulo.valorPago = $scope.titulo.valorTitulo + $scope.titulo.valorJuros + $scope.titulo.valorJurosCalculado - $scope.titulo.valorDesconto;
                }
            }

            function calculoPorcentagemJuros(valorJurosCalculado) {
                $scope.porcentagemJuros = valorJurosCalculado / $scope.titulo.valorTitulo * 100;
            }

            function descontoCalculado(porcentagemDesconto) {
                $scope.titulo.valorDesconto = $scope.titulo.valorTitulo / 100 * porcentagemDesconto;
                 $scope.titulo.valorPago = $scope.titulo.valorTitulo + $scope.titulo.valorJuros + $scope.titulo.valorJurosCalculado - $scope.titulo.valorDesconto;
            }

            function calculoPorcentagemDesconto(valorDesconto) {
                $scope.porcentagemDesconto = valorDesconto / $scope.titulo.valorTitulo * 100;
            }



            function buscarTitulo(titulo) {

                $http.get("http://localhost:8888/processofinanceiro/services/titulos/" + titulo.numero)
                    .then(function (response) {
                        $scope.titulo = response.data;

                         $scope.titulo.valorPago = $scope.titulo.valorTitulo + $scope.titulo.valorJuros + $scope.titulo.valorJurosCalculado - $scope.titulo.valorDesconto;
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





            function inserirTitulo(titulo) {


                $http.post("http://localhost:8888/processofinanceiro/services/titulos/", $scope.titulo)
                    .then(function (response) {
                        console.log(titulo);



                        growl.success(response.data.mensagem);


                        if ($scope.titulo.tipo == 'pagar') {
                            setTimeout(function () {
                                $window.location.href = 'listagemContasPagar.html'
                            }, 3000);
                        } else {
                            setTimeout(function () {
                                $window.location.href = 'listagemContasReceber.html'
                            }, 3000);
                        }

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



            function alterarTitulo(titulo) {



                $http.put("http://localhost:8888/processofinanceiro/services/titulos/", $scope.titulo)
                    .then(function (response) {




                        growl.success(response.data.mensagem);

                        document.getElementById("MeuForm").reset();
                        if ($scope.titulo.tipo == 'pagar') {
                            setTimeout(function () {
                                $window.location.href = 'listagemContasPagar.html'
                            }, 3000);
                        } else {
                            setTimeout(function () {
                                $window.location.href = 'listagemContasReceber.html'
                            }, 3000);
                        }

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


            function cadastrar(titulo) {




                inserirTitulo(titulo);



            }

            function alterar(titulo) {




                alterarTitulo(titulo);



            }










            $scope.init = function () {

                $('.data').mask('00-00-0000');
              

                $scope.currentItem = JSON.parse($window.sessionStorage.getItem('titulo'));
                $scope.titulo.tipo = $window.sessionStorage.getItem('tipo');
                if ($scope.currentItem.numero != null) {

                    buscarTitulo($scope.currentItem);

                }

                if ($scope.currentItem.dataCriacao == null) {
                    $scope.titulo.dataCriacao = moment(new Date()).format('DD-MM-YYYY');
                }

                $window.sessionStorage.setItem('titulo', 'false');


            };


        }
    ]);