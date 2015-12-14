function getId(name)
{
	var i = name.indexOf(".section");
	var idField = name.substring(0, i);			
	var field = document.getElementById(idField + '.id');
	var encodedId = URLEncode(field.value);
	return encodedId;
}
function URLEncode(id)
{
	// The Javascript escape and unescape functions do not correspond
	// with what browsers actually do...
	var SAFECHARS = "0123456789" +					// Numeric
					"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +	// Alphabetic
					"abcdefghijklmnopqrstuvwxyz" +
					"-_.!~*'()";					// RFC2396 Mark characters
	var HEX = "0123456789ABCDEF";

	var plaintext = id;
	var encoded = "";
	for (var i = 0; i < plaintext.length; i++ ) {
		var ch = plaintext.charAt(i);
	    if (ch == " ") {
		    encoded += "+";				// x-www-urlencoded, rather than %20
		} else if (SAFECHARS.indexOf(ch) != -1) {
		    encoded += ch;
		} else {
		    var charCode = ch.charCodeAt(0);
			if (charCode > 255) {
			    alert( "Unicode Character '" 
                        + ch 
                        + "' cannot be encoded using standard URL encoding.\n" +
				          "(URL encoding only supports 8-bit characters.)\n" +
						  "A space (+) will be substituted." );
				encoded += "+";
			} else {
				encoded += "%";
				encoded += HEX.charAt((charCode >> 4) & 0xF);
				encoded += HEX.charAt(charCode & 0xF);
			}
		}
	} // for
	return encoded;
}
function editCell(e){
  var rows = document.getElementById('myTable').rows;
  for (i=0; i < rows.length; i++) {
  	if(rows[i].className == "selected")
	  	rows[i].className =  "unselected";
  }
  
  var td = (e.target)?e.target:e.srcElement;
  while(td&&td.nodeName!="TD"){
    td=td.parentNode;
  }
  if(td){
    var rw = td.parentNode;
    if(rw.className == 'unselected')
	    rw.className = 'selected';
  }
}

// TableSorter.js
function move(dir) {
	var tableEl = document.getElementById('myTable');
	for (var i = 0; i < tableEl.rows.length; i++)
   	{
		if(tableEl.rows[i].className == "selected"){
			if((i == 0) && dir < 0)
				return;
			if((i == tableEl.rows.length - 1) && dir > 0)
				return;
			if(dir > 0){
				//if down button pressed
				
				var next = tableEl.rows[i + 1].cells[3].firstChild.value;
				tableEl.rows[i + 1].cells[3].firstChild.value = 
						tableEl.rows[i].cells[3].firstChild.value;
				tableEl.rows[i].cells[3].firstChild.value = next;					
        	}else{
				//if up button pressed
				var next = tableEl.rows[i - 1].cells[3].firstChild.value;
				tableEl.rows[i - 1].cells[3].firstChild.value = 
						tableEl.rows[i].cells[3].firstChild.value;
				tableEl.rows[i].cells[3].firstChild.value = next;					
        	}
        	break;
        }
	}
	sort();	
}

function sort()
{
	var tableEl = document.getElementById('myTable');
	var columnData = new Array(tableEl.rows.length)
	var tbody = tableEl.tBodies[0];
	
	for (var i = 0; i < tableEl.rows.length; i++)
	{
		columnData[i] = tableEl.rows[i].cells[3].firstChild.value
	}
	columnData.sort()
	var newTbody = document.createElement('tbody');
	
	for (var i = 0; i < columnData.length; i++)
	{
		for (var j = 0; j < tableEl.rows.length; j++)
	    {
	    	if (columnData[i] == tableEl.rows[j].cells[3].firstChild.value)
	        {
	    	    newTbody.appendChild(tableEl.rows[j]);
				break;
			 }	         
	     }
	}	
    tableEl.replaceChild(newTbody, tbody);
}