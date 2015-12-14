// *****************************************************************************
//                  Cross-Browser Javascript pop-up calendar.
//
// Author  : Anthony Garrett
//
// Credits : I wrote this from scratch myself but I couldn't have done it
//           without the superb "JavaScript The Definitive Guide" by David
//           Flanagan (Pub. O'Reilly ISBN 0-596-00048-0).  I also recognise
//           a contribution from my experience with PopCalendar 4.1 by
//           Liming(Victor) Weng.
//
// Rights  : Feel free to copy and change this as you like except that I
//           regard it as polite to leave the first twenty-one lines as is.
//
// Contact : Sorry, I can't offer support for this but if you find a problem
//           (or just want to tell me how useful you find it), please send
//           me an email at scwfeedback@tarrget.info (Note the two Rs in 
//           tarrget).  I will try to fix problems quickly but this is a 
//           spare time thing for me.
//
// *****************************************************************************
//
// Features: Easily customised
//                  (output date format, colours, language, year range)
//           Accepts a date as input
//                  (see comments below for formats).
//           Cross-browser code tested against;
//                  Internet Explorer 6.0.28     Mozilla  1.7.1
//                  Opera             7.52       Firefox  0.9.1
//
// Note    : The year range is honoured by all the ways of changing between
//           months however I have decided not to prevent a user from selecting
//           a display day that is outside the range (it can happen if the
//           day falls in the week that starts or ends the range).  You may
//           wish to add further validation to your page in order to trap those
//           days.
//
// *****************************************************************************
//
// Version   Date        By               Description
// =======   ====        ===============  ===========
//   1.0     2004-08-02  Anthony Garrett  Initial release
//
// *****************************************************************************

// Customisation variables

	var calBackground      = '#F0FFFF',
		calCellBackground  = '#DCDCDC',
		calHighlight       = '#85EEFE',
		calTodayText       = '#666362',
		calCellText        = '#000000',
		calWeekendText     = '#CC6666',
		calExMonthText     = '#999999',
		calBaseYear        =  1998,
		calDropDownYears   =  10,
		calToday           = 'Today',
		calClear		   = 'Clear',
		calInvalidDateMsg  = 'The entered date is invalid.\n',
		calOutOfRangeMsg   = 'The entered date is out of range.\n',
		calInvalidAlert    = ['Invalid date (',') ignored.'],
		calWeekInits       = ['S','M','T','W','T','F','S'],
		calMonthName       = ['Jan','Feb','Mar','Apr','May','Jun',
							  'Jul','Aug','Sep','Oct','Nov','Dec'];

		// If you are looking for the places to modify the input, output and 
		// displayed date formats there is only one of each and they can all
		// be found by searching the code below for the string;
		//                    date format here

// End of customisation variables

	var calSourceEle,
		calDateNow   = new Date(),
		calStartDate = new Date(),
		calMonthSum  = 0;

	function showCal(ele)

//*************************************************************************************
//    Take a preset value.  The routine does the minimum of parsing and
//    validation so it's up to you to pass in a valid date obeying the
//    following rules;
//
//       The delimiter is one of the following four characters   /  .  ,  -
//         Day, month and year are entered
//           (see code comments for date format changes) OR
//         Month and Year are entered (in that order)    OR
//         only the Year is entered
//       Day is a number (valid for the month) between 1 and 31
//       Month is a number between 1 and 12
//         or a three letter abbreviation of the defined month name
//         (see customisation variables above for the abbreviation array)
//	     Year is two or four digits.
//
//       If the year is two digits then the routine assumes a year belongs 
//       in the 21st Century unless it is less than 50 in which case it 
//       assumes the 20th Century is intended.
//
//    The value is used as the seed for the pop-up calendar.
//    If no value is preset then the seed date is 
//      Today (when today is in range) OR
//      The middle of the date range.

	  {if (ele.value.length==0)
		 {// If no value is entered and today is within the range,
	      // use today's date, otherwise use the middle of the valid range.
		  var seedDate = new Date(calDateNow);
		  if ((new Date(calBaseYear+calDropDownYears-1,11,31)) < seedDate ||
			  (new Date(calBaseYear,0,1))                      > seedDate)
		    {seedDate = new Date(calBaseYear + 
				                 Math.floor(calDropDownYears / 2), 5, 1);
		    }
		 }
	   else
		 {// Parse the string into an array using the allowed delimiters
		  arrSeedDate = ele.value.split(/[\/,\-,\.,\,]/);

          // **********************************************
          // ** You may alter the input date format here **
          // **********************************************
          switch (arrSeedDate.length)
            {case 1:
				{// Year only entry
		         calDay   = 1;
		         calMonth = '6';
			     calYear  = parseInt(arrSeedDate[0]);
			     break;}
			 case 2:
				{// Year and Month entry
				 calDay   = 1;
			     calMonth = arrSeedDate[0];
			     calYear  = parseInt(arrSeedDate[1]);
			     break;}
		     case 3:
				{// Day Month and Year entry
				 calDay   = parseInt(arrSeedDate[0]);
			     calMonth = arrSeedDate[1];
			     calYear  = parseInt(arrSeedDate[2]);
				 // for Month, Day and Year entry use...
				 //   calDay   = parseInt(arrSeedDate[1]);
				 //   calMonth = arrSeedDate[0];
				 //   calYear  = parseInt(arrSeedDate[2]);
			     break;}
            }

		  // So now we have the Day, Month and Year.

          if (calYear<100)
		    {calYear = calYear+parseInt((calYear>50)?1900:2000);}

          // Check whether the month is in digits or an abbreviation
				
		  if (calMonth.search(/\d+/)==0) {
		  	seedDate = new Date(calYear, calMonth, calDay);
		  }
		  else
			{month = calMonthName.join('|').toUpperCase().
				  	 search(calMonth.substr(0,3).toUpperCase());
			 seedDate = new Date(calYear, Math.floor(month/4), calDay);
			}
		 }

          // Test that we have arrived at a valid date

       if (isNaN(seedDate))
         {alert(calInvalidDateMsg +
			    calInvalidAlert[0] + ele.value +
				calInvalidAlert[1]);
		  seedDate = new Date(calBaseYear + 
					          Math.floor(calDropDownYears / 2), 5, 1);
		 }
	   else
		 {// Test that the date is within range,
		  // if not then set date to middle of range.

		  if ((new Date(calBaseYear+calDropDownYears-1,11,31)) < seedDate ||
			  (new Date(calBaseYear,0,1))                      > seedDate)
		    {alert(calOutOfRangeMsg +
		           calInvalidAlert[0] + ele.value +
			       calInvalidAlert[1]);
		     seedDate = new Date(calBaseYear + 
				                 Math.floor(calDropDownYears / 2), 5, 1);
		    }
	     }

	   // Calculate the number of months that the entered (or
	   // defaulted) month is after the start of the allowed
	   // date range.

	   calMonthSum = 12*(seedDate.getFullYear()-calBaseYear)+
		             seedDate.getMonth();

	   // Set the drop down boxes.

	   document.getElementById('calYears').options.selectedIndex =
		   Math.floor(calMonthSum/12);
		var index = calMonthSum%12;
		index = index -1;
	   document.getElementById('calMonths').options.selectedIndex=
		   (index);

	   // Position the calendar box

	   var offsetTop =parseInt(ele.offsetTop)+parseInt(ele.offsetHeight),
		   offsetLeft=parseInt(ele.offsetLeft);

	   calSourceEle=ele;

	   do {ele=ele.parentNode;
		   if (ele.tagName!='FORM'  && ele.tagName!='TR' &&
			   ele.tagName!='TBODY' && ele.nodeType==1)
			 {offsetTop +=parseInt(ele.offsetTop);
			  offsetLeft+=parseInt(ele.offsetLeft);
			 }
		  }
	   while (ele.tagName!='BODY');

	   document.getElementById('cal').style.top =offsetTop +'px';
	   document.getElementById('cal').style.left=offsetLeft+'px';

	   // Display the month

	   showMonth(0);

	   // Show it on the page

	   document.getElementById('cal').style.visibility='visible';
	  }

	function hideCal(ele)
	  {calStartDate.setDate(calStartDate.getDate() +
							parseInt(ele.id.substr(8)));
	   setOutput(calStartDate);
	  }

	function setOutput(outputDate)
	  {// ******************************************************
	   // ***   You can change the output date format here   ***
	   // ******************************************************
	   
	   var day = outputDate.getDate();
	   if (day <= 9) {
	   		day = "0" + day;
	   }	
	   var month = outputDate.getMonth();
	   month = month + 1;
	   if (month <=9) {
	   		month = "0" + month;
	   }	
	   calSourceEle.value = day  + '/' +
							month + '/' +
							outputDate.getFullYear();
	  }
	  
	  function clearDate() {
	  	calSourceEle.value = "";
	  }

	function showMonth(bias)
	  {// Set the selectable Month and Year
	   // May be called: from the left and right arrows
	   //                  (shift month -1 and +1 respectively)
	   //                from the month selection list
	   //                from the year selection list
	   //                from the showCal routine
	   //                  (which initiates the display).

	   var calShowDate  = new Date();

	   selYears  = document.getElementById('calYears');
	   selMonths = document.getElementById('calMonths');

	   if (selYears.options.selectedIndex>-1)
		 {calMonthSum=12*(selYears.options.selectedIndex)+bias;
		  if (selMonths.options.selectedIndex>-1)
			{calMonthSum+=selMonths.options.selectedIndex;}
		 }
	   else
		 {if (selMonths.options.selectedIndex>-1)
		   {calMonthSum+=selMonths.options.selectedIndex;}
		 }

	   calShowDate.setFullYear(calBaseYear + Math.floor(calMonthSum/12),
							   (calMonthSum%12),
							   1);

	   if ((12*(parseInt(calShowDate.getFullYear()) - calBaseYear)) +
		   parseInt(calShowDate.getMonth()) < (12*calDropDownYears)     &&
		   (12*(parseInt(calShowDate.getFullYear()) - calBaseYear)) +
		   parseInt(calShowDate.getMonth()) > -1)
		 {selYears.options.selectedIndex=Math.floor(calMonthSum/12);
		  selMonths.options.selectedIndex=(calMonthSum%12);

		  curMonth = calShowDate.getMonth();

		  calShowDate.setDate(-calShowDate.getDay()+1);

		  calStartDate = new Date(calShowDate);

		  // Treewalk to display the dates.
		  // I tried to use getElementsByName but IE refused to cooperate
		  // so I resorted to this method which works for all tested browsers.

		  var cells = document.getElementById('calCells');
		  for (i=0;i<cells.childNodes.length;i++)
			{var rows = cells.childNodes[i];
			 if (rows.nodeType==1 && rows.tagName=='TR')
			   {for (j=0;j<rows.childNodes.length;j++)
				 {var cols = rows.childNodes[j];
				  if (cols.nodeType==1 && cols.tagName=='TD')
					{rows.childNodes[j].innerHTML=calShowDate.getDate();
					 rows.childNodes[j].style.color=
						 (calShowDate.getMonth()!=curMonth)
							 ?calExMonthText
							 :(calShowDate.getDay()==0||calShowDate.getDay()==6)
								  ?calWeekendText
								  :calCellText;
					 calShowDate.setDate(calShowDate.getDate()+1);
					}
				  }
			   }
			}
		 }
	  }

   document.write(
	 "<table id='cal' style='visibility:hidden;position:absolute;" +
							"top:0px;left:0px;z-index:1;" +
							"width:200px;height:200px;text-align:center;" +
							"background-color:" + calBackground +
							";border:ridge 2px;font-size:10pt;" +
							"font-family:Arial,Helvetica;" +
							"font-weight:bold;'>" +
	   "<tr>" +
		 "<td>" +
		   "<table cellspacing='0' cellpadding='0' width='100%'>" +
			"<tr><td><input type='button' value='<' " +
							"style='height:25px;width:25px;" +
								   "vertical-align:center;" +
								   "text-align:center;" +
							       "font-size:12pt;font-family:fixedSys;" +
								   "font-weight:bold;' " +
							"onclick = 'showMonth(-1);' /></td>" +
				 "<td><select id='calMonths' onChange='showMonth(0);'>");

   for (i=0;i<calMonthName.length;i++)
	 document.write(   "<option>" + calMonthName[i] + "</option>");

   document.write("   </select>" +
				 "</td>" +
				 "<td><select id='calYears' onChange='showMonth(0);'>");

   for (i=0;i<calDropDownYears;i++)
	 document.write(   "<option>" + (calBaseYear+i) + "</option>");

   document.write(   "</select>" +
				 "</td>" +
				 "<td><input type='button' value='>' " +
							"style='height:25px;width:25px;" +
								   "vertical-align:center;" +
								   "text-align:center;" +
								   "font-size:12pt;font-family:fixedSys;" +
								   "font-weight:bold;' " +
							"onclick='showMonth(1);' /></td>" +
				"</tr>" +
			  "</table>" +
			"</td>" +
		  "</tr>" +
		  "<tr>" +
			"<td style='text-align:center'>" +
			  "<table style='width:100%;text-align:right;" +
							"font-size:10pt;font-family:Arial,Helvetica;'" +
					 "onclick=\"document.getElementById('cal').style." +
										"visibility='hidden';\">" +
				"<thead>" +
				  "<tr style='color:" + calCellBackground + ";" +
							 "text-align:center;'>");

   for (i=0;i<calWeekInits.length;i++)
	 document.write("<td>" + calWeekInits[i] + "</td>");

   document.write("</tr>" +
				"</thead>" +
				"<tbody id='calCells'>");

   for (i=0;i<6;i++)
	 {document.write(
				  "<tr>");
	  for (j=0;j<7;j++)
		{document.write(
					"<td id='calCell_" + (j+(i*7))+ "' " +
						"style='height:20px;width:20px;" +
							   "background-color:" +
								  calCellBackground + ";' " +
						"onmouseover=\"this.style.backgroundColor='" +
								  calHighlight     + "';\" " +
						"onmouseout =\"this.style.backgroundColor='" +
								  calCellBackground + "';\" " +
						"onclick='hideCal(this);'></td> ");
		}

	  document.write(
				  "</tr>");
	 }

   document.write(
				"</tbody>");

   if ((new Date(calBaseYear + calDropDownYears, 11, 32)) > calDateNow &&
	   (new Date(calBaseYear, 0, 0))                      < calDateNow)
	 {document.write(
				  "<tfoot>" +
					"<tr>" +
					  "<td colspan='4' " +
						  "style='text-align:left;font-weight:normal;" +
								 "color:" + calTodayText + ";' " +
						  "onmouseover=\"this.style.color='" +
														calHighlight + "';" +
										"this.style.fontWeight='bold';\" " +
						  "onmouseout =\"this.style.color='" +
														calTodayText + "';" +
										"this.style.fontWeight='normal';\" " +
							"onclick='setOutput(calDateNow);'>" +
														calToday + " ");

	  // *********************************************************
	  // ***   You can change the displayed date format here   ***
	  // *********************************************************
	  //document.write(calDateNow.getDate()                + "/" +
	//				 calMonthName[calDateNow.getMonth()] + "/" +
	//				 calDateNow.getFullYear());

	  document.write("</td>");
	  document.write("<td colspan='3' " + 
	  						"style='text-align:right;font-weight:normal;" +
								 "color:" + calTodayText + ";' " +
						  	"onmouseover=\"this.style.color='" +
														calHighlight + "';" +
										"this.style.fontWeight='bold';\" " +
						  	"onmouseout =\"this.style.color='" +
														calTodayText + "';" +
										"this.style.fontWeight='normal';\" " +
							"onclick='clearDate();'>" +
														calClear + " </td>");
	  
						
	  document.write("</tr>" +
				   "</tfoot>");
	 }

   document.write(
			  "</table>" +
			"</td>" +
		  "</tr>" +
		"</table>");

// End of Calendar
