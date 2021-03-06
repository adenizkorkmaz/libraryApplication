angular.module("sprang.services", ["ngResource"]).
    factory('Book', function ($resource) {
        var Book = $resource('/api/books/:bookId', {bookId: '@id'},
            {update: {method: 'PUT'}});
        Book.prototype.isNew = function () {
            return (typeof(this.id) === 'undefined');
        }
        return Book;
    });

angular.module("sprang", ["sprang.services"]).
    config(function ($routeProvider) {
        $routeProvider
            .when('/', {templateUrl: '/views/books/list.html', controller: BookListController})
    }).directive('modalDialog', function () {
        return {
            restrict: 'E',
            scope: {
                show: '='
            },
            replace: true, // Replace with the template below
            transclude: true, // we want to insert custom content inside the directive
            link: function (scope, element, attrs) {
                scope.dialogStyle = {};
                if (attrs.width)
                    scope.dialogStyle.width = attrs.width;
                if (attrs.height)
                    scope.dialogStyle.height = attrs.height;
                scope.hideModal = function () {
                    scope.show = false;
                };
            },
            template: "<div class='ng-modal' ng-show='show'><div class='ng-modal-overlay' ng-click='hideModal()'></div><div class='ng-modal-dialog' ng-style='dialogStyle'><div class='ng-modal-close' ng-click='hideModal()'>X</div><div class='ng-modal-dialog-content' ng-transclude></div></div></div>"
        };
    }).directive('loading', function () {
        return {
            restrict: 'E',
            replace:true,
            template: '<div class="loading"><img src="/img/ajax-loader-.gif" width="50" height="50" />LOADING...</div>',
            link: function (scope, element, attr) {
                scope.$watch('loading', function (val) {
                    if (val)
                        $(element).show();
                    else
                        $(element).hide();
                });
            }
        }
    });

function BookListController($scope, Book, $http) {
    $scope.loading = true;
    $scope.books = Book.query();
    $scope.editMode = false;
    $scope.modalPanel = false;
    $scope.captcha = null;

    $scope.deleteBook = function (book) {
        book.$delete(function () {
            var dlg = confirm('Do you want to delete this record?');
            if (dlg) {
                $scope.books.splice($scope.books.indexOf(book), 1);
                toastr.success("Deleted");
            } else {
                //do nothing
            }
        });
    };
    $scope.generateCap = function () {
        if ($scope.captcha == null) {
            var captcha = new CAPTCHA({
                selector: '#captcha',
                width: 300,
                height: 150,
                onSuccess: function () {
                    $scope.createBook();

                }
            });
            $scope.captcha = captcha;
        }

        $scope.captcha.generate();

    };

    $scope.openModal = function () {
        $scope.generateCap();
        $scope.modalPanel = true;
        $scope.book = new Book();
    };

    $scope.editBook = function (book) {
        $scope.book = book;
        $scope.editMode = true;
        $scope.modalPanel = true;

    };

    $scope.createBook = function (book) {
        $scope.loading = true;
        if (validateInputs(book)) {
            $scope.book.$save(function (book, headers) {
                $scope.modalPanel = false;
                toastr.success("Created");
                $scope.books = Book.query();
                $scope.loading = false;
            });
        }
    }

    $scope.updateBook = function (book) {
        if (validateInputs(book)) {
            $scope.book.$update(function () {
                $scope.modalPanel = false;
                $scope.editMode = false;
                toastr.success("Updated");
            });
        }
    }

    var validateInputs = function (book) {
        if ($scope.book.title == 'undefined' || $scope.book.title == null || $scope.book.title.length <= 0) {
            toastr.error('Please enter title');
            return false;

        }
        if ($scope.book.author.firstName == 'undefined' || $scope.book.author.firstName == null || $scope.book.author.firstName.length <= 0) {
            toastr.error('Please enter author first name');
            return false;
        }

        if ($scope.book.author.lastName == 'undefined' || $scope.book.author.lastName == null || $scope.book.author.lastName.length <= 0) {
            toastr.error('Please enter author last name');
            return false;
        }


        return true;
    };

    $scope.loading = false;
}