$(document).ready(function(){
    modal();
});

function modal() {
    $("#appModal").modal({
            keyboard: true,
            backdrop: "static",
            show: false,
    }).on("show.bs.modal", function(event){
        let button = $(event.relatedTarget);
        let action = button.data('action');
        let id = button.data('id');

        switch(action) {
            case 'viewBook':
                viewBookById($(this), id);
                break;
        }

    }).on('hidden.bs.modal', function(event){
        $(this).find('.modal-body').find('#id').html('');
        $(this).find('.modal-body').find('#title').html('');
        $(this).find('.modal-body').find('#edition').html('');
        $(this).find('.modal-body').find('#author').html('');
        $(this).find('.modal-body').find('#yearPublsihed').html('');
        $(this).find('.modal-body').find('#description').html('');
        $(this).find('.modal-body').find('#category').html('');
    });
}

function viewBookById(modal, id) {
    let url = '/books/' + id;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => {modal.find('#id').html(data.id);
            modal.find('#title').html(data.title);
            modal.find('#edition').html(data.edition);
            modal.find('#author').html(data.author);
            modal.find('#yearPublsihed').html(data.yearPublished);
            modal.find('#description').html(data.description);
            modal.find('#category').html(data.category.name);
        })
        .catch(error => console.error(error));
}