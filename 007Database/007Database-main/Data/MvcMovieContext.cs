using System;
using JamesBondMovieDatabase.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using MvcMovie.Models;

namespace MvcMovie.Data
{
    public class MvcMovieContext : DbContext
    {
        public MvcMovieContext(DbContextOptions options) : base(options)
        {
        }

        public DbSet<Movie> Movies { get; set; }
        public DbSet<Review> Reviews { get; set; }

        // Could use SqlServer using connection below if installed
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder
                /** use simple logging to log the sql commands issued by entityframework **/
                //.LogTo(Console.WriteLine, LogLevel.Information)
                //.EnableSensitiveDataLogging()
                .UseSqlite("Filename=movies.db");
        }

        public void Initialise()
        {
            Database.EnsureDeleted();
            Database.EnsureCreated();
        }
    }
}
