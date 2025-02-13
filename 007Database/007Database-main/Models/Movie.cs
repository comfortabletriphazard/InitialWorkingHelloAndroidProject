using System;
using System.Collections.Generic;
using System.Linq;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using JamesBondMovieDatabase.Models;

namespace MvcMovie.Models
{
    public class Movie
    {
        public int Id { get; set; }
        public string Title { get; set; }

        [DataType(DataType.Date)]
        public DateTime Year { get; set; }
        public int Duration { get; set; }       
        public double Budget { get; set; }
        public string Director { get; set; }
        public string Cast { get; set; }
        public string Synopsis { get; set; }
        public string Photo { get; set; }
        public int Rating
        {
            get
            {
                var count = Reviews.Count > 0 ? Reviews.Count : 1;
                return Reviews.AsEnumerable().Sum(r => r.Rating) / count * 10;
            }
        }
        // EF Relationship - a movie can have many reviews 
        public IList<Review> Reviews { get; set; } = new List<Review>();

    }

}
