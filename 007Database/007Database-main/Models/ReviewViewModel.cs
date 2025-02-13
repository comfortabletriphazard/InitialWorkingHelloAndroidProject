using System;
using System.Web;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;

namespace JamesBondMovieDatabase.Models
{

    //ViewModel to promote decoupling and populate review table
    public class TableMovieTitle
    {

        public int Id { get; set; }
        public string Title { get; set; }

        // name of reviewer
        public string Name { get; set; }

        // date review was made
        [Display(Name = "Created On")]
        [DataType(DataType.Date)]
        public DateTime CreatedOn { get; set; }

        // reviewer comments
        public string Comment { get; set; }

        // value between 1-10
        public int Rating { get; set; }

        // EF Dependant Relationship Review belongs to a Movie
        [Display(Name = "Title")]
        public int MovieId { get; set; }

    }

}
