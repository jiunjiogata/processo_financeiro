angular.module('ProcessoFinanceiro')
  .controller('PessoasCtrlEditar', ['$scope', '$http', '$window', '$location', 'growl', 'moment',
    function ($scope, $http, $window, $location, growl, moment) {

      $scope.cadastrar = cadastrar;



      $scope.modo;

      $scope.currentItem;



      function buscarPessoaCpfCnpj(pessoa) {

        $http.get("http://localhost:8888/processofinanceiro/services/pessoas/" + pessoa.cpfCnpj)
          .then(function (response) {
            $scope.pessoa = response.data;
            if ($scope.pessoa.numero == 0) {
              $scope.pessoa.numero = '';
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





      function inserirPessoa(pessoa) {
        $scope.validado = null;

        $http.post("http://localhost:8888/processofinanceiro/services/pessoas/", $scope.pessoa)
          .then(function (response) {
            console.log(pessoa);
            $scope.pessoa = response.data;



            growl.success(response.data.mensagem);


            setTimeout(function () {
              $window.location.href = 'listagemPessoas.html'
            }, 3000);

          }).catch(function onError(response) {


            if (response.status == 500 || response.data.mensagem == null) {
              $scope.erro = "Server Error 500";
              growl.error("Erro no servidor");

            } else {
              console.log(response.data.mensagem);
              growl.error(response.data.mensagem);
            }



          });

      }



      function alterarPessoa(pessoa) {



        $http.put("http://localhost:8888/processofinanceiro/services/pessoas/", $scope.pessoa)
          .then(function (response) {

            $scope.pessoa = response.data;


            growl.success(response.data.mensagem);

            document.getElementById("MeuForm").reset();
            setTimeout(function () {
              $window.location.href = 'listagemPessoas.html'
            }, 3000);

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


      function cadastrar(pessoa) {




        if ($scope.modo == 'alteracao') {
          alterarPessoa(pessoa);



        } else {

          inserirPessoa(pessoa);



        }


      }







      $scope.init = function () {

          $('.data').mask('00-00-0000');

        $scope.currentItem = JSON.parse($window.sessionStorage.getItem('pessoa'));
        if ($scope.currentItem.cpfCnpj != null) {

          buscarPessoaCpfCnpj($scope.currentItem);
          $scope.modo = 'alteracao';

        }

        $window.sessionStorage.setItem('pessoa', 'false');


      };


    }
  ]);