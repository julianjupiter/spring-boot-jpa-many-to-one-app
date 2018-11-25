let modal = $('#defaultModal');

$(document).ready(function(){
    defaultModal();
    viewBookModal();
    updateBookModal();
    onClickButton();
});

function defaultModal() {
    modal.modal({
            keyboard: true,
            backdrop: "static",
            show: false,
    }).on('hidden.bs.modal', function(event){
        $(this).find('.modal-title').html('');
        $(this).find('.modal-body').html('');
        $(this).find('.modal-footer').html('');
        window.location.reload();
    });
}

function viewBookModal() {
    $("#viewBookModal").modal({
            keyboard: true,
            backdrop: "static",
            show: false,
    }).on("show.bs.modal", function(event){
        let button = $(event.relatedTarget);
        let id = button.data('id');
        findBookById($(this), id);
    }).on('hidden.bs.modal', function(event){
        $(this).find('.modal-body').find('#id').html('');
        $(this).find('.modal-body').find('#title').html('');
        $(this).find('.modal-body').find('#edition').html('');
        $(this).find('.modal-body').find('#author').html('');
        $(this).find('.modal-body').find('#description').html('');
        $(this).find('.modal-body').find('#category').html('');
    });
}

function updateBookModal() {
    $("#updateBookModal").modal({
            keyboard: true,
            backdrop: "static",
            show: false,
    }).on("show.bs.modal", function(event){
        let button = $(event.relatedTarget);
        let id = button.data('id');
        findBookById($(this), id);
    }).on('hidden.bs.modal', function(event){
        $(this).find('.modal-body').find('#id').val('');
        $(this).find('.modal-body').find('#title').val('');
        $(this).find('.modal-body').find('#edition').val('');
        $(this).find('.modal-body').find('#author').val('');
        $(this).find('.modal-body').find('#description').val('');
        $(this).find('.modal-body').find('#category').val('');
    });
}

function onClickButton() {
    let form = $('#updateBookForm');

    $('#updateBookButton').click(function(e){
        let id = form.find('#id').val().trim();
        let title = form.find('#title').val().trim();
        let edition = form.find('#edition').val().trim();
        let author = form.find('#author').val().trim();
        let description = form.find('#description').val().trim();
        let category = form.find('#category').val().trim();
        let data = {
            id: id,
            title: title,
            edition: edition,
            author: author,
            description, description,
            categoryId: category
        };
        updateBook(data, id);
    });
}

function findBookById(modal, id) {
    let modalId = modal.attr('id');
    let url = '/books/' + id;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            switch(modalId) {
                case 'viewBookModal':
                    viewBook(modal, data);
                    break;

                case 'updateBookModal':
                    editBook(modal, data);
                    break;
            }
        })
        .catch(error => console.error(error));
}

function updateBook(data, id) {
    let url = '/books/' + id;
    fetch(url, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.status == 200) {
            modal.find('.modal-title').html('Success');
            modal.find('.modal-body').html('Successful update!');
            modal.modal('show');
            $('#defaultModal').modal('show');
            $('#updateBookModal').modal('hide');
        } else {
            modal.find('.modal-title').html('Error');
            modal.find('.modal-body').html('An error occurred!');
        }
    })
    .catch(error => console.error(error));
}

function viewBook(modal, data) {
    modal.find('#id').html(data.id);
    modal.find('#title').html(data.title);
    modal.find('#edition').html(data.edition);
    modal.find('#author').html(data.author);
    modal.find('#description').html(data.description);
    modal.find('#category').html(data.category.name);
}

function editBook(modal, data) {
    modal.find('#id').val(data.id);
    modal.find('#title').val(data.title);
    modal.find('#edition').val(data.edition);
    modal.find('#author').val(data.author);
    modal.find('#description').val(data.description);
    modal.find('#category').html(data.category.name);
    modal.find('#category').append(new Option(data.category.name, data.category.id))
}