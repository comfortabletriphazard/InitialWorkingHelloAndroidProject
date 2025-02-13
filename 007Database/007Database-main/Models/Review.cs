using System;
using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using MvcMovie.Models;

namespace JamesBondMovieDatabase.Models
{
    public class Review
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

        // Navigation property
        public Movie Movie { get; set; }

    }
}