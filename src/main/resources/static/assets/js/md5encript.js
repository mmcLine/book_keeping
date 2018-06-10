function loginEncript() {

    $("#doc-ipt-tel-2").val(strEnc($("#doc-ipt-tel-1").val(),"11234456"));

    $("#doc-ipt-pwd-2").val(strEnc($("#doc-ipt-pwd-1").val(),"11234456"));

}